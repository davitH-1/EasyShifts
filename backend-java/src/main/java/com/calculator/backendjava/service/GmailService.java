package com.calculator.backendjava.service;

import com.calculator.backendjava.model.Shift;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class GmailService {

    private static final String SCHEDULE_SENDER = "schedules@premierevaletparking.com";
    private static final String GMAIL_BASE = "https://gmail.googleapis.com/gmail/v1/users/me";

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final PdfService pdfService;
    private final ShiftAutomationService shiftAutomationService;
    private final WebClient webClient;

    public GmailService(OAuth2AuthorizedClientService authorizedClientService,
                        PdfService pdfService,
                        ShiftAutomationService shiftAutomationService) {
        this.authorizedClientService = authorizedClientService;
        this.pdfService = pdfService;
        this.shiftAutomationService = shiftAutomationService;
        this.webClient = WebClient.builder().baseUrl(GMAIL_BASE).build();
    }

    public List<Shift> syncScheduleEmails(OAuth2AuthenticationToken auth) throws Exception {
        String token = authorizedClientService
            .loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName())
            .getAccessToken().getTokenValue();

        List<String> messageIds = listMessageIds(token);
        List<Shift> allShifts = new ArrayList<>();

        for (String messageId : messageIds) {
            for (byte[] pdfBytes : getPdfAttachments(token, messageId)) {
                String text = pdfService.extractText(new ByteArrayInputStream(pdfBytes));
                allShifts.addAll(shiftAutomationService.parseAndSave(text));
            }
        }
        return allShifts;
    }

    private List<String> listMessageIds(String token) {
        Map<String, Object> response = webClient.get()
            .uri(u -> u.path("/messages")
                .queryParam("q", "from:" + SCHEDULE_SENDER)
                .build())
            .header("Authorization", "Bearer " + token)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .block();

        if (response == null || !response.containsKey("messages")) return List.of();

        return ((List<Map<String, Object>>) response.get("messages"))
            .stream()
            .map(m -> (String) m.get("id"))
            .toList();
    }

    @SuppressWarnings("unchecked")
    private List<byte[]> getPdfAttachments(String token, String messageId) {
        Map<String, Object> message = webClient.get()
            .uri("/messages/" + messageId)
            .header("Authorization", "Bearer " + token)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .block();

        if (message == null) return List.of();

        Map<String, Object> payload = (Map<String, Object>) message.get("payload");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) payload.get("parts");
        if (parts == null) return List.of();

        List<byte[]> pdfs = new ArrayList<>();
        collectPdfs(token, messageId, parts, pdfs);
        return pdfs;
    }

    @SuppressWarnings("unchecked")
    private void collectPdfs(String token, String messageId,
                              List<Map<String, Object>> parts, List<byte[]> pdfs) {
        for (Map<String, Object> part : parts) {
            // Recurse into nested multipart
            List<Map<String, Object>> nested = (List<Map<String, Object>>) part.get("parts");
            if (nested != null) {
                collectPdfs(token, messageId, nested, pdfs);
                continue;
            }

            String mimeType = (String) part.getOrDefault("mimeType", "");
            String filename = (String) part.getOrDefault("filename", "");
            boolean isPdf = "application/pdf".equals(mimeType)
                || filename.toLowerCase().endsWith(".pdf");
            if (!isPdf) continue;

            Map<String, Object> body = (Map<String, Object>) part.get("body");
            if (body == null) continue;

            String data;
            String attachmentId = (String) body.get("attachmentId");
            if (attachmentId != null) {
                Map<String, Object> att = webClient.get()
                    .uri("/messages/" + messageId + "/attachments/" + attachmentId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
                data = att != null ? (String) att.get("data") : null;
            } else {
                data = (String) body.get("data");
            }

            if (data != null) {
                pdfs.add(Base64.getUrlDecoder().decode(data));
            }
        }
    }
}
package com.calculator.backendjava.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class AiService {

    private final ChatClient chatClient;
    private final WebClient webClient;

    public AiService(
        ChatClient.Builder chatClientBuilder,
        @Value("${ai.service.url:http://localhost:8000}") String aiServiceUrl
    ) {
        this.chatClient = chatClientBuilder.build();
        this.webClient = WebClient.builder().baseUrl(aiServiceUrl).build();
    }

    public String chat(String prompt) {
        return chatClient.prompt().user(prompt).call().content();
    }

    public String chatWithData(String prompt, Map<String, Object> data) {
        return chat(prompt + "\n\nData: " + data);
    }

    public Mono<Map<String, Object>> parsePdf(byte[] pdfBytes, String filename) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ByteArrayResource(pdfBytes) {
            @Override public String getFilename() { return filename; }
        }).contentType(MediaType.APPLICATION_PDF);

        return webClient.post()
            .uri("/parse/pdf")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
package com.calculator.backendjava.controller;

import com.calculator.backendjava.model.Shift;
import com.calculator.backendjava.service.GmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gmail")
public class GmailController {

    private final GmailService gmailService;

    public GmailController(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @PostMapping("/sync")
    public ResponseEntity<Map<String, Object>> sync(OAuth2AuthenticationToken auth) {
        if (auth == null) return ResponseEntity.status(401).build();

        try {
            List<Shift> shifts = gmailService.syncScheduleEmails(auth);
            return ResponseEntity.ok(Map.of(
                "synced", shifts.size(),
                "message", "Parsed " + shifts.size() + " shift(s) from schedule emails"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", e.getMessage()));
        }
    }
}
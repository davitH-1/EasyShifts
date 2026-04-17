package com.calculator.backendjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(Map.of(
            "name", orEmpty(principal.getAttribute("name")),
            "email", orEmpty(principal.getAttribute("email")),
            "avatarUrl", orEmpty(principal.getAttribute("picture"))
        ));
    }

    private String orEmpty(Object value) {
        return value != null ? value.toString() : "";
    }
}
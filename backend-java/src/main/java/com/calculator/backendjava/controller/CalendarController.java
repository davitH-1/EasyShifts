package com.calculator.backendjava.controller;

import com.calculator.backendjava.service.CalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/this-week")
    public ResponseEntity<Map<String, Object>> getThisWeek(OAuth2AuthenticationToken auth) {
        if (auth == null) {
            return ResponseEntity.status(401).build();
        }
        Map<String, Object> events = calendarService.getThisWeekEvents(auth);
        String analysis = calendarService.analyzeWithAi(events);
        return ResponseEntity.ok(Map.of("events", events, "analysis", analysis));
    }
}
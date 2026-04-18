package com.calculator.backendjava.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.DayOfWeek;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class CalendarService {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final AiService aiService;
    private final WebClient webClient;

    public CalendarService(OAuth2AuthorizedClientService authorizedClientService, AiService aiService) {
        this.authorizedClientService = authorizedClientService;
        this.aiService = aiService;
        this.webClient = WebClient.builder()
            .baseUrl("https://www.googleapis.com/calendar/v3")
            .build();
    }

    public Map<String, Object> getThisWeekEvents(OAuth2AuthenticationToken auth) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
            auth.getAuthorizedClientRegistrationId(), auth.getName()
        );
        String accessToken = client.getAccessToken().getTokenValue();

        ZonedDateTime startOfWeek = ZonedDateTime.now(ZoneOffset.UTC)
            .with(DayOfWeek.MONDAY)
            .truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime endOfWeek = startOfWeek.plusDays(7);

        return webClient.get()
            .uri(uri -> uri.path("/calendars/primary/events")
                .queryParam("timeMin", startOfWeek.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .queryParam("timeMax", endOfWeek.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .queryParam("singleEvents", true)
                .queryParam("orderBy", "startTime")
                .build())
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .block();
    }

    public String analyzeWithAi(Map<String, Object> calendarData) {
        return aiService.chatWithData(
            "Analyze these Google Calendar events for this week. " +
            "Identify busy periods, free time slots, and suggest the best times for work shifts.",
            calendarData
        );
    }
}
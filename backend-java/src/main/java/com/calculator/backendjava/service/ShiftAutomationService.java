package com.calculator.backendjava.service;

import com.calculator.backendjava.model.Shift;
import com.calculator.backendjava.model.ValetShift;
import com.calculator.backendjava.repository.ShiftRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ShiftAutomationService {

    private final ChatClient chatClient;
    private final ShiftRepository shiftRepository;
    private final LocationWhitelistService whitelist;

    public ShiftAutomationService(ChatClient.Builder chatClientBuilder,
                                   ShiftRepository shiftRepository,
                                   LocationWhitelistService whitelist) {
        this.chatClient = chatClientBuilder.build();
        this.shiftRepository = shiftRepository;
        this.whitelist = whitelist;
    }

    public List<Shift> parseAndSave(String rawPdfText) {
        List<ValetShift> parsed = chatClient.prompt()
            .user(u -> u.text("""
                Extract all available valet shifts from the following schedule text.
                Return ONLY a JSON array of objects.
                Use these keys: "date" (YYYY-MM-DD), "startTime" (h:mma), "endTime" (h:mma), and "location".

                Schedule text:
                {text}
                """).param("text", rawPdfText))
            .call()
            .entity(new ParameterizedTypeReference<List<ValetShift>>() {});

        return parsed.stream()
            .map(this::toShift)
            .map(shiftRepository::save)
            .toList();
    }

    private Shift toShift(ValetShift v) {
        LocalDate date = parseDate(v.date());
        BigDecimal hours = calculateHours(v.startTime(), v.endTime());
        Shift shift = new Shift(v.location(), date, v.startTime(), v.endTime(), hours);
        shift.setStatus(whitelist.isAllowed(v.location())
            ? Shift.ShiftStatus.PENDING
            : Shift.ShiftStatus.REJECTED);
        return shift;
    }

    private LocalDate parseDate(String raw) {
        try {
            return LocalDate.parse(raw, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    private BigDecimal calculateHours(String start, String end) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("h:mma");
            LocalTime s = LocalTime.parse(start.toUpperCase(), fmt);
            LocalTime e = LocalTime.parse(end.toUpperCase(), fmt);
            long minutes = ChronoUnit.MINUTES.between(s, e);
            if (minutes < 0) minutes += 1440;
            return BigDecimal.valueOf(minutes / 60.0);
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }
}
package com.calculator.backendjava.controller;

import com.calculator.backendjava.model.Shift;
import com.calculator.backendjava.repository.ShiftRepository;
import com.calculator.backendjava.service.PdfService;
import com.calculator.backendjava.service.ShiftAutomationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class ShiftController {

    private final PdfService pdfService;
    private final ShiftAutomationService shiftAutomationService;
    private final ShiftRepository shiftRepository;

    public ShiftController(PdfService pdfService, ShiftAutomationService shiftAutomationService,
                           ShiftRepository shiftRepository) {
        this.pdfService = pdfService;
        this.shiftAutomationService = shiftAutomationService;
        this.shiftRepository = shiftRepository;
    }

    @GetMapping("/api/jobs")
    public List<Map<String, Object>> getJobs() {
        return shiftRepository.findAll().stream()
            .map(this::toJobResponse)
            .toList();
    }

    @PostMapping("/api/shifts/parse-pdf")
    public ResponseEntity<List<Map<String, Object>>> parsePdf(
        @RequestParam("file") MultipartFile file
    ) throws Exception {
        String text = pdfService.extractText(file.getInputStream());
        List<Shift> saved = shiftAutomationService.parseAndSave(text);
        return ResponseEntity.ok(saved.stream().map(this::toJobResponse).toList());
    }

    private Map<String, Object> toJobResponse(Shift s) {
        return Map.of(
            "id", s.getId().toString(),
            "locationName", s.getLocation() != null ? s.getLocation() : "",
            "dateTime", s.getShiftDate() != null ? s.getShiftDate().toString() : "",
            "startTime", s.getStartTime() != null ? s.getStartTime() : "",
            "endTime", s.getEndTime() != null ? s.getEndTime() : "",
            "hours", s.getHours() != null ? s.getHours().doubleValue() : 0,
            "status", s.getStatus().name().toLowerCase()
        );
    }
}
package com.calculator.backendjava.controller;

import com.calculator.backendjava.model.ValetShift;
import com.calculator.backendjava.service.PdfService;
import com.calculator.backendjava.service.ShiftAutomationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin(origins = "http://localhost:4200")
public class ShiftController {

    private final PdfService pdfService;
    private final ShiftAutomationService aiService;

    public ShiftController(PdfService pdfService, ShiftAutomationService aiService) {
        this.pdfService = pdfService;
        this.aiService = aiService;
    }

    @PostMapping("/parse-pdf")
    public List<ValetShift> extractShifts(@RequestParam("file") MultipartFile file) throws Exception {
        String text = pdfService.extractText(file.getInputStream());
        return aiService.parseShiftsWithAI(text);
    }
}
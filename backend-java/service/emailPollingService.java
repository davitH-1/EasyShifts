package com.easyshifts.service;

import com.easyshifts.service.ShiftAutomationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class emailPollingService {
    private final pdfService pdfParser;
    private final ShiftAutomationService automation;

    public emailPollingService(pdfService pdfParser, ShiftAutomationService automation) {
        this.pdfParser = pdfParser;
        this.automation = automation;
    }

    @Scheduled(fixedRate = 60000)
    public void pollForSchedules() {
        // 1. Use JavaMailSender to fetch unread emails
        // 2. If PDF found:
        //    InputStream is = attachment.getInputStream();
        //    String text = pdfParser.extractText(is);
        //    automation.processSchedule(text);
        System.out.println("Bot: Checking inbox for new schedules...");
    }
}
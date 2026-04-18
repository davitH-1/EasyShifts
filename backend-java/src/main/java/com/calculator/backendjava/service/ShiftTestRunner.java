package com.calculator.backendjava.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ShiftTestRunner implements CommandLineRunner {

    private final ShiftAutomationService aiService;

    public ShiftTestRunner(ShiftAutomationService aiService) {
        this.aiService = aiService;
    }

    @Override
    public void run(String... args) {
        // This runs automatically when you hit 'Run' in IntelliJ
        aiService.runSelfTest();
    }
}
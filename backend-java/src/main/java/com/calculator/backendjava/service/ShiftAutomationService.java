package com.calculator.backendjava.service;

import com.calculator.backendjava.model.ValetShift;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShiftAutomationService {

    private final ChatClient chatClient;

    // Spring Boot provides a ChatClient.Builder automatically
    public ShiftAutomationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<ValetShift> parseShiftsWithAI(String rawPdfText) {
        return this.chatClient.prompt()
                .user(u -> u.text("""
                    Extract all available valet shifts from the following schedule text.
                    Return ONLY a JSON array of objects.
                    Use these keys: "date" (YYYY-MM-DD), "startTime", "endTime", and "location".
                    
                    Schedule text:
                    {text}
                    """).param("text", rawPdfText))
                .call()
                .entity(new ParameterizedTypeReference<List<ValetShift>>() {});
    }
    // --- TEST FUNCTION ---
    public void runSelfTest() {
        System.out.println("\n[INTERNAL TEST] Starting AI Logic Check...");

        // Simulating the "Bad Scheduling System" text
        String dummyPdfText = """
            VALET SCHEDULE WEEK 12
            Monday April 20th: Shift at Hilton Anaheim 4pm-11pm.
            Openings: Wednesday at Spectrum Center 10am to 6pm.
            Please email back ASAP to claim.
            """;

        try {
            List<ValetShift> results = parseShiftsWithAI(dummyPdfText);

            if (results != null && !results.isEmpty()) {
                System.out.println("✅ TEST PASSED: AI successfully parsed dummy text.");
                System.out.println("   Found: " + results.size() + " shifts.");
                results.forEach(s -> System.out.println("   -> " + s.location() + " at " + s.startTime()));
            } else {
                System.out.println("❌ TEST FAILED: AI returned empty list.");
            }
        } catch (Exception e) {
            System.out.println("❌ TEST FAILED: Error communicating with Ollama: " + e.getMessage());
        }
    }
}
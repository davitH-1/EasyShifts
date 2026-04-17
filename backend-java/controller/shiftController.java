package com.easyshifts.controller;

import com.easyshifts.ValetShift;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin(origins = "http://localhost:4200")
public class shiftController {

    // For demo purposes, we'll store shifts in memory.
    // In production, you'd use a database.
    private final List<ValetShift> shiftHistory = new ArrayList<>();

    @GetMapping("/history")
    public List<ValetShift> getHistory() {
        return shiftHistory;
    }

    @PostMapping("/add")
    public void addShift(@RequestBody ValetShift shift) {
        shiftHistory.add(shift);
    }
}
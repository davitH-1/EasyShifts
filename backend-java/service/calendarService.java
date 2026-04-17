package com.easyshifts.service;

import org.springframework.stereotype.Service;

@Service
public class calendarService {
    public boolean hasConflict(String date, String startTime) {
        // TODO: Integrate with Google Calendar API
        // Return true if you have an event during this shift
        return false;
    }
}
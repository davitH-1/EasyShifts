package com.easyshifts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class mapService {
    @Value("${valet.home-address}")
    private String homeAddress;

    public int getTravelTime(String destination) {
        // TODO: Integrate with Google Maps Distance Matrix API
        // Return travel time in minutes based on destination
        return 20;
    }
}
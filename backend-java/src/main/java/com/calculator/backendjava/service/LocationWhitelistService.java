package com.calculator.backendjava.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LocationWhitelistService {

    private static final Set<String> WHITELIST = Set.of(
        "corona del mar",
        "irvine"
    );

    public boolean isAllowed(String location) {
        if (location == null) return false;
        String lower = location.toLowerCase();
        return WHITELIST.stream().anyMatch(lower::contains);
    }
}
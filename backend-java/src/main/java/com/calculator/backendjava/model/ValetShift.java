package com.calculator.backendjava.model;

public record ValetShift(
        String date,
        String startTime,
        String endTime,
        String location
) {}
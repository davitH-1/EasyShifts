package com.calculator.backendjava.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shifts")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String location;

    @Column(name = "shift_date")
    private LocalDate shiftDate;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    private BigDecimal hours;

    @Enumerated(EnumType.STRING)
    private ShiftStatus status = ShiftStatus.PENDING;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum ShiftStatus { PENDING, CONFIRMED, REJECTED }

    public Shift() {}

    public Shift(String location, LocalDate shiftDate, String startTime, String endTime, BigDecimal hours) {
        this.location = location;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hours = hours;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public LocalDate getShiftDate() { return shiftDate; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public BigDecimal getHours() { return hours; }
    public ShiftStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setShiftDate(LocalDate shiftDate) { this.shiftDate = shiftDate; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setHours(BigDecimal hours) { this.hours = hours; }
    public void setStatus(ShiftStatus status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
}
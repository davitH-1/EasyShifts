package com.calculator.backendjava.repository;

import com.calculator.backendjava.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {}
package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {

    @Query("SELECT AVG(sr.value) FROM SensorReading sr WHERE sr.timestamp BETWEEN :from AND :to AND sr.sensor.type = 'TEMPERATURE'")
    Double findAverageTemperatureBetween(LocalDateTime from, LocalDateTime to);
}

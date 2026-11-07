package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {

    @Query("SELECT AVG(sr.value) FROM SensorReading sr WHERE sr.timestamp BETWEEN :from AND :to AND sr.sensor.type = 'TEMPERATURE'")
    Double findAverageTemperatureBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT sr.value FROM SensorReading sr WHERE sr.sensor.type = :sensorType AND sr.timestamp BETWEEN :from AND :to ORDER BY sr.timestamp ASC")
    List<Double> findValuesBySensorTypeAndTimestampBetween(SensorType sensorType, LocalDateTime from, LocalDateTime to);
}

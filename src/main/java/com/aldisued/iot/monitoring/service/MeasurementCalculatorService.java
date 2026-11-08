package com.aldisued.iot.monitoring.service;


import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    if (deviation == null || deviation < 0 || deviation > 1) {
      throw new IllegalArgumentException("Deviation must be between 0.0 and 1.0.");
    }
    return values.stream()
        .filter(value -> Math.abs(value - calculateAverage(values)) <= deviation * calculateAverage(values))
        .toList();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    // TODO: Task 10
    return List.of();
  }

  private double calculateAverage(List<Double> values) {
    return values.stream()
        .mapToDouble(Double::doubleValue)
        .average()
        .orElse(0.0);
  }

}

package com.aldisued.iot.monitoring.service;


import java.util.List;
import java.util.stream.IntStream;
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
    if (windowSize <= 0 || windowSize > data.size()) {
      throw new IllegalArgumentException("Window size must be greater than 0 and less than or equal to the size of the data.");
    }
    return IntStream.range(0, data.size() - windowSize + 1)
        .mapToDouble(i -> calculateAverage(data.subList(i, i + windowSize)))
        .boxed()
        .toList();
  }

  private double calculateAverage(List<Double> values) {
    return values.stream()
        .mapToDouble(Double::doubleValue)
        .average()
        .orElse(0.0);
  }

}

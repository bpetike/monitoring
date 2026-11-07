package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

  private final AlertRepository alertRepository;
  private final SensorRepository sensorRepository;
  private final KafkaTemplate<String, AlertDto> kafkaTemplate;

  public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
      KafkaTemplate<String, AlertDto> kafkaTemplate) {
    this.alertRepository = alertRepository;
    this.sensorRepository = sensorRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  public Alert saveAlert(AlertDto alertDto) {
    Alert alert = new Alert();
    alert.setSensor(sensorRepository.findById(alertDto.sensorId()).orElseThrow());
    alert.setMessage(alertDto.message());
    alert.setTimestamp(alertDto.timestamp());

    Alert savedAlert = alertRepository.save(alert);
    kafkaTemplate.send("alerts", alertDto);
    return savedAlert;
  }

  public AlertDto findLastAlertBySensorId(UUID sensorId) {
    return alertRepository.findTopBySensorIdOrderByTimestampDesc(sensorId)
        .map(alert -> new AlertDto(alert.getSensor().getId(), alert.getMessage(), alert.getTimestamp()))
        .orElse(null);
  }
}

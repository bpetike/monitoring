package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

  private final SensorRepository sensorRepository;

  public SensorService(SensorRepository sensorRepository) {
    this.sensorRepository = sensorRepository;
  }

  public Sensor saveSensor(SensorDto sensor) {
    return sensorRepository.save(new Sensor(
        sensor.name(),
        sensor.type()
    ));
  }

  public boolean existsByName(String name) {
    Sensor sensor = sensorRepository.findByName(name);
    if (sensor == null) {
      return false;
    }
    return true;
    
  }
}


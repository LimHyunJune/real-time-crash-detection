package com.example.sensor.controller;

import com.example.sensor.dto.Payload;
import com.example.sensor.dto.RawData;
import com.example.sensor.dto.SensorData;
import com.example.sensor.producer.SensorProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    SensorProducer sensorProducer;


    @PostMapping("/")
    public void run(@RequestBody SensorData data)
    {
        List<Payload> payloads = data.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        for(Payload payload : payloads)
        {
            if(payload.getName().equals("gyroscope") || payload.getName().equals("accelerometer"))
            {
                try {
                    String respData = objectMapper.writeValueAsString(payload.getValues());
                    RawData raw = objectMapper.readValue(
                            respData, RawData.class);
                    raw.setName(payload.getName());
                    if(payload.getName().equals("gyroscope"))
                        sensorProducer.sendGyrData(raw);
                    if(payload.getName().equals("accelerometer"))
                        sensorProducer.sendAccData(raw);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

}

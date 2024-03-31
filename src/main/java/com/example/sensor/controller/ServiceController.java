package com.example.sensor.controller;

import com.example.sensor.dto.Payload;
import com.example.sensor.dto.SensorData;
import com.example.sensor.model.Gravity;
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

    ObjectMapper objectMapper;

    @PostMapping("/")
    public void run(@RequestBody SensorData data)
    {
        List<Payload> payloads = data.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        for(Payload payload : payloads)
        {
            if(payload.getName().equals("gravity"))
            {
                try {
                    String respData = objectMapper.writeValueAsString(payload.getValues());
                    Gravity gravity = objectMapper.readValue(
                            respData, Gravity.class);
                    sensorProducer.sendGravityData(gravity);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

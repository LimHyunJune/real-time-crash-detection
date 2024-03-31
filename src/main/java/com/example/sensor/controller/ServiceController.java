package com.example.sensor.controller;

import com.example.sensor.dto.Payload;
import com.example.sensor.dto.SensorData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @PostMapping("/")
    public void run(@RequestBody SensorData data)
    {
        List<Payload> payloads = data.getPayload();
        for(Payload payload : payloads)
        {
            System.out.println(payload);
        }
    }

}

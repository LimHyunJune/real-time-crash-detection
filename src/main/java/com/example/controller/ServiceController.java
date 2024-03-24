package com.example.controller;

import com.example.dto.Payload;
import com.example.dto.SensorData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @PostMapping("/run")
    public void run(@RequestBody SensorData data)
    {
        List<Payload> payloads = data.getPayload();
        for(Payload payload : payloads)
        {

        }
    }
}

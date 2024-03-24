package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorData {
    int messageId;
    String sessionId;
    String deviceId;
    List<Payload> payload;

}

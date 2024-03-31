package com.example.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payload {

    String name;
    Object values;
    int accuracy;
    Long time;
}

package com.example.sensor.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawData {
    String name;
    Double x;
    Double y;
    Double z;
}

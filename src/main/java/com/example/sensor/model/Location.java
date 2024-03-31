package com.example.sensor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    Integer bearingAccuracy;
    Double speedAccuracy;
    Float verticalAccuracy;
    Float horizontalAccuracy;
    Float speed;
    Float bearing;
    Float altitude;
    Float longitude;
    Float latitude;
}

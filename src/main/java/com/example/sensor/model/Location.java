package com.example.sensor.model;

import lombok.Data;

@Data
public class Location {
    String name;
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

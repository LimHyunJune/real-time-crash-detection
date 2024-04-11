package com.example.sensor;

import com.example.sensor.model.CrashDetection;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorApplication.class, args);
		CrashDetection crashDetection = new CrashDetection();
		crashDetection.run();

	}

}

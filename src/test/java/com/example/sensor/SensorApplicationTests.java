package com.example.sensor;

import com.example.sensor.model.Gravity;
import com.example.sensor.producer.SensorProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SensorApplicationTests {

	SensorProducer sensorProducer = new SensorProducer();
	@Test
	void contextLoads() {
		Gravity gravity = Gravity.builder().x((float)1.123).y((float)1.223).z((float)1.333).build();
		sensorProducer.sendGravityData(gravity);
	}

}

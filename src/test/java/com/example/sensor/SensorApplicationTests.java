package com.example.sensor;

import com.example.sensor.consumer.AccConsumer;
import com.example.sensor.consumer.GyrConsumer;
import com.example.sensor.dto.RawData;
import com.example.sensor.producer.SensorProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SensorApplicationTests {

	@Test
	void producerTest() {
		SensorProducer sensorProducer = new SensorProducer();
		RawData raw = RawData.builder().name("acc").x((float)1.123).y((float)1.223).z((float)1.333).build();
		sensorProducer.sendRawData(raw);
	}

	@Test
	void consumerTest()
	{
		AccConsumer accConsumer = new AccConsumer();
		accConsumer.consume();

		GyrConsumer gyrConsumer = new GyrConsumer();
		gyrConsumer.consume();
	}

}

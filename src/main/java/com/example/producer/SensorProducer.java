package com.example.producer;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SensorProducer {
    Properties props = new Properties();
    String topic = "";

    SensorProducer(String name)
    {
        topic = name;
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }
}

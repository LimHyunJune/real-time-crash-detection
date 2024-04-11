package com.example.sensor.producer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CrashProducer {
    Properties props = new Properties();
    KafkaProducer<String, Boolean> producer;

    public CrashProducer()
    {
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    public void sendCrashData(boolean crash)
    {
        ProducerRecord<String, Boolean> record = new ProducerRecord<>("crash", crash);
        producer.send(record);
    }
}

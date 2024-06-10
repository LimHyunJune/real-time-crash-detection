package com.example.sensor.producer;


import com.example.sensor.dto.RawData;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Properties;

@Component
public class SensorProducer {
    Properties props = new Properties();
    KafkaProducer<String, GenericRecord> producer;

    GenericRecord avroRecordValue;

    /*
    * For Crash Detection
    * 1. The current acceleration value is measured eight times in a row with a sharp change from the previous acceleration value
    * 2. Measurement of the highest acceleration value above the accident determination threshold and the angular velocity value above the appropriate range
    * */



    @Value("${acc.threshold}")
    double accThreshold;
    @Value("${gyr.threshold}")
    double gyrThreshold;

    public SensorProducer()
    {
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", "http://192.168.56.101:8081");
        producer = new KafkaProducer<>(props);

        Schema.Parser parser = new Schema.Parser();
        String myAvroSchemaValue = "{"
                + "\"namespace\": \"myrecord\","
                + " \"name\": \"raw_value\","
                + " \"type\": \"record\","
                + " \"fields\": ["
                + "     {\"name\": \"amount\", \"type\": \"double\"}"
                + " ]"
                + "}";
        Schema schemaValue = parser.parse(myAvroSchemaValue);
        avroRecordValue = new GenericData.Record(schemaValue);

    }

    public void sendAccData(RawData raw)
    {
        double currentAccAmount = getAmount(raw);

        avroRecordValue.put("amount", currentAccAmount);
        ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(raw.getTopic(),raw.getDuid(), avroRecordValue);
        producer.send(record);
    }
    
    public void sendGyrData(RawData raw)
    {
        double currentGyrAmount = getAmount(raw);
        avroRecordValue.put("amount", currentGyrAmount);
        ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(raw.getTopic(), raw.getDuid(),  avroRecordValue);
        producer.send(record);
    }

    private Double getAmount(RawData raw)
    {
        return Math.sqrt(raw.getX() * raw.getX() + raw.getY() * raw.getY() + raw.getZ() * raw.getZ());
    }
}

package com.example.sensor.producer;


import com.example.sensor.dto.RawData;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SensorProducer {
    Properties props = new Properties();
    KafkaProducer<GenericRecord, GenericRecord> producer;

    public SensorProducer()
    {
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", "http://192.168.56.101:8081");
        producer = new KafkaProducer<>(props);
    }

    public void sendRawData(RawData raw)
    {
        Schema.Parser parser = new Schema.Parser();
        String myAvroSchemaKey = "{"
                + "\"namespace\": \"myrecord\","
                + " \"name\": \"raw_key\","
                + " \"type\": \"record\","
                + " \"fields\": ["
                + "     {\"name\": \"name\", \"type\": \"string\"}"
                + " ]"
                + "}";
        Schema schemaKey = parser.parse(myAvroSchemaKey);

        GenericRecord avroRecordKey = new GenericData.Record(schemaKey);
        avroRecordKey.put("name", raw.getName());


        String myAvroSchemaValue = "{"
                + "\"namespace\": \"myrecord\","
                + " \"name\": \"raw_value\","
                + " \"type\": \"record\","
                + " \"fields\": ["
                + "     {\"name\": \"x\", \"type\": \"float\"},"
                + "     {\"name\": \"y\",  \"type\": \"float\"},"
                + "     {\"name\": \"z\", \"type\": \"float\"}"
                + " ]"
                + "}";
        Schema schemaValue = parser.parse(myAvroSchemaValue);

        GenericRecord avroRecordValue = new GenericData.Record(schemaValue);
        avroRecordValue.put("x", raw.getX());
        avroRecordValue.put("y", raw.getY());
        avroRecordValue.put("z", raw.getZ());

        ProducerRecord<GenericRecord, GenericRecord> record = new ProducerRecord<>(raw.getName(), avroRecordKey, avroRecordValue);
        producer.send(record);
    }
}

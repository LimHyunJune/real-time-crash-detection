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
    KafkaProducer<String, GenericRecord> producer;

    public SensorProducer()
    {
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", "http://192.168.56.101:8081");
        producer = new KafkaProducer<>(props);
    }

    public void sendRawData(RawData raw)
    {
        Schema.Parser parser = new Schema.Parser();
        String myAvroSchema = "{"
                + "\"namespace\": \"myrecord\","
                + " \"name\": \"raw\","
                + " \"type\": \"record\","
                + " \"fields\": ["
                + "     {\"name\": \"x\", \"type\": \"float\"},"
                + "     {\"name\": \"y\",  \"type\": \"float\"},"
                + "     {\"name\": \"z\", \"type\": \"float\"}"
                + " ]"
                + "}";
        Schema schema = parser.parse(myAvroSchema);

        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("x", raw.getX());
        avroRecord.put("y", raw.getY());
        avroRecord.put("z", raw.getZ());

        ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(raw.getName(), raw.getName(), avroRecord);
        producer.send(record);
    }
}

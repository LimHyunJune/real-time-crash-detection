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
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SensorProducer {
    Properties props = new Properties();
    KafkaProducer<Integer, GenericRecord> producer;

    GenericRecord avroRecordValue;

    /*
    * For Crash Detection
    * 1. The current acceleration value is measured eight times in a row with a sharp change from the previous acceleration value
    * 2. Measurement of the highest acceleration value above the accident determination threshold and the angular velocity value above the appropriate range
    * */
    int accCount = 0;
    double beforeAccAmount = 0.0;

    final double accThreshold = 13.0;
    final double gyrThreshold = 10.0;

    public SensorProducer()
    {
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
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
        if(accCount < 8)
        {
            if(currentAccAmount > beforeAccAmount)
            {
                accCount++;
                if(accCount >= 8 )
                {
                    if(currentAccAmount >= accThreshold)
                    {
                        avroRecordValue.put("amount", currentAccAmount);
                        ProducerRecord<Integer, GenericRecord> record = new ProducerRecord<>(raw.getName(),1, avroRecordValue);
                        producer.send(record);
                    }
                    accCount = 0;
                }
            }
            else
            {
                accCount = 0;
            }

        }
        beforeAccAmount = currentAccAmount;

    }

    public void sendGyrData(RawData raw)
    {
        double currentGyrAmount = getAmount(raw);
        if(currentGyrAmount >= gyrThreshold)
        {
            avroRecordValue.put("amount", currentGyrAmount);
            ProducerRecord<Integer, GenericRecord> record = new ProducerRecord<>(raw.getName(), 1,  avroRecordValue);
            producer.send(record);
        }
    }

    private Double getAmount(RawData raw)
    {
        return Math.sqrt(raw.getX() * raw.getX() + raw.getY() * raw.getY() + raw.getZ() * raw.getZ());
    }
}

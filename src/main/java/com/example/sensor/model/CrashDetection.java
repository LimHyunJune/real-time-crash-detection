package com.example.sensor.model;

import com.example.sensor.consumer.AccConsumer;
import com.example.sensor.consumer.GyrConsumer;
import com.example.sensor.producer.CrashProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;

public class CrashDetection {

    AccConsumer accConsumer = new AccConsumer();
    GyrConsumer gyrConsumer = new GyrConsumer();

    CrashProducer crashProducer = new CrashProducer();

    // 가속도 임계값
    float accPoint = (float) 0.1;
    // 각속도 임계값 ㅣ
    float gyrPoint = (float) 0.1;

    public void run()
    {
        while(true)
        {
            try
            {
                crashProducer.sendCrashData(checkAcc(accConsumer.consume())
                        && checkGyr(gyrConsumer.consume()));
            }
            catch (WakeupException e)
            {
                return;
            }
        }
    }

    private boolean checkAcc(ConsumerRecords<String, Float> accRecords)
    {
        double beforeAcc = 0;
        int count = 0;

        for(ConsumerRecord record : accRecords)
        {
            double curAcc = (double) record.value();

            if(curAcc - beforeAcc > 0)
                count += 1;
            else
                count = 1;
            beforeAcc = curAcc;

            if(count >= 8 && curAcc >= accPoint)
                return true;
        }
        return false;
    }

    private boolean checkGyr(ConsumerRecords<String, Float> gyrRecords)
    {
        for(ConsumerRecord record : gyrRecords)
        {
            float curGyr = (float) record.value();
            if(curGyr >= gyrPoint)
                return true;
        }
        return false;
    }
}

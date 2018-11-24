package com.motek.btsAnalisys;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.Arrays;

import BTSEvents.BTSEvent;
import akka.actor.ActorRef;
import com.motek.btsAnalisys.actors.event.command.PassEvent;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
public class KafkaEventConsumer {

    private ActorRef eventAgent;

    public KafkaEventConsumer(ActorRef eventAgent) {
        this.eventAgent = eventAgent;
    }


    public  void runEventConsumer() throws Exception {
        //Kafka consumer configuration settings
        String topicName = "test3";
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer
                <String, byte[]>(props);

        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Arrays.asList(topicName));

        //print the topic name
        System.out.println("Subscribed to topic " + topicName);
        int i = 0;

        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);

            for (ConsumerRecord<String, byte[]> record : records) {

                ByteArrayInputStream bis = new ByteArrayInputStream(record.value());
                ObjectInput in = new ObjectInputStream(bis);
                BTSEvent btsEvent =  BTSEvent.class.cast(in.readObject());
                // print the offset,key and value for the consumer records.
                //if(btsEvent instanceof SomeBTSEvent)
                //    System.out.println("hurray");
                eventAgent.tell(new PassEvent(btsEvent,btsEvent.getId()), ActorRef.noSender());
                //System.out.printf("offset = %d, key = %s, value = %s\n",
                //        record.offset(), record.key(), btsEvent.getId());
            }
        }
    }
}

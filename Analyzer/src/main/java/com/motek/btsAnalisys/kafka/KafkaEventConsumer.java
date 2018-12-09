package com.motek.btsAnalisys.kafka;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Arrays;

import BTSEvents.BTSEvent;
import BTSEvents.EnteredBTSArea;
import BTSEvents.LeftBTSArea;
import akka.actor.ActorRef;
import com.motek.btsAnalisys.actors.manager.commands.CreateAgent;
import com.motek.btsAnalisys.actors.manager.commands.KillAgent;
import com.motek.btsAnalisys.actors.manager.commands.PassEvent;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
public class KafkaEventConsumer {

    private ActorRef managingAgent;

    public KafkaEventConsumer(ActorRef managingAgent) {
        this.managingAgent = managingAgent;
    }


    public  void runEventConsumer() throws Exception {
        //Kafka consumer configuration settings
        String topicName = "event-topic";
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
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(1));

            for (ConsumerRecord<String, byte[]> record : records) {

                ByteArrayInputStream bis = new ByteArrayInputStream(record.value());
                ObjectInput in = new ObjectInputStream(bis);
                BTSEvent btsEvent =  (BTSEvent)in.readObject();
                // print the offset,key and value for the consumer records.
                //if(btsEvent instanceof SomeBTSEvent)
                //    System.out.println("hurray");


                if(btsEvent instanceof EnteredBTSArea){
                    managingAgent.tell(new CreateAgent(btsEvent.getId()), ActorRef.noSender());
                }
                else if(btsEvent instanceof LeftBTSArea){
                    managingAgent.tell(new KillAgent(btsEvent.getId()), ActorRef.noSender());
                }
                else{
                    managingAgent.tell(new PassEvent(btsEvent,btsEvent.getId()), ActorRef.noSender());
                }

                //System.out.printf("offset = %d, key = %s, value = %s\n",
                //        record.offset(), record.key(), btsEvent.getId());
            }
        }
    }
}

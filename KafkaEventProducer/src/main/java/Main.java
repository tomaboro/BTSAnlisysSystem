import BTSEvents.BTSEvent;
import BTSEvents.SomeBTSEvent;
import BTSEvents.UserEntered;
import BTSEvents.UserLeft;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception{

        //Assign topicName to string variable
        String topicName = "test3";

        // create instance for properties to access producer configs
        Properties props = new Properties();

        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");

        //Set acknowledgements for producer requests.
        props.put("acks", "all");

        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);

        //Specify buffer size in config
        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.ByteArraySerializer");

        Producer<String, byte[]> producer = new KafkaProducer
                <String, byte[]>(props);

        String id = "abc";
        List<BTSEvent> events = Arrays.asList(new UserEntered(id), new SomeBTSEvent(id), new SomeBTSEvent(id), new UserLeft(id));

        for(BTSEvent event : events){
            byte[] serializedEvent = serializeBTSEvent(event);
            producer.send(new ProducerRecord<String, byte[]>(topicName,
                    event.getClass().toString(), serializedEvent));
            System.out.println("Message sent successfully");

            TimeUnit.SECONDS.sleep(2);
        }

        producer.close();
    }

    static byte [] serializeBTSEvent(BTSEvent event) throws IOException {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos)){
            out.writeObject(event);
            byte b[] = bos.toByteArray();
            return b;
        }

    }
}

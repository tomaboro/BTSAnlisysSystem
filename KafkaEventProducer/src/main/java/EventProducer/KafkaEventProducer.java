package EventProducer;

import BTSEvents.*;
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

public class KafkaEventProducer {

    private String topicName;
    private Properties props;
    private Producer<String, byte[]> producer;

    public KafkaEventProducer(String topicName) {
        this.topicName = topicName;
        initialize();
    }

    public void start() throws IOException, InterruptedException {
        String id = "abc";
        List<BTSEvent> events = Arrays.asList(new EnteredBTSArea(id,19.934847,50.054043), new Sms(id,19.934847,50.054043),
                new Sms(id,19.934847,50.054043), new LeftBTSArea(id,19.934847,50.054043));

        for(BTSEvent event : events){
            byte[] serializedEvent = serializeBTSEvent(event);
            producer.send(new ProducerRecord<String, byte[]>(topicName,
                    event.getClass().toString(), serializedEvent));
            System.out.println("Message sent successfully");

            TimeUnit.SECONDS.sleep(2);
        }

        producer.close();
    }

    private void initialize(){
        // create instance for properties to access producer configs
        props = new Properties();

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

        producer = new KafkaProducer<String, byte[]>(props);
    }

    static byte [] serializeBTSEvent(BTSEvent event) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(event);
            byte b[] = bos.toByteArray();
            return b;
        }
    }
}

package EventProducer;

import BTSEvents.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.Place;
import utils.Places;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class KafkaEventProducer implements Runnable {

    private String topicName;
    private Properties props;
    private Producer<String, byte[]> producer;
    private boolean isDelayed;

    public KafkaEventProducer(String topicName, boolean isDelayed) {
        this.topicName = topicName;
        this.isDelayed = isDelayed;
        initialize();
    }

    public void run() {
        try {
            String id = UUID.randomUUID().toString();

            BTSEvent event;

            // Entry event
            event = new UserEntered(id);
            serializeAndSend(event, producer, topicName, isDelayed ? 2 : 0);


            // first location
            Place randomPlace = Places.RandomEntryPlace();
            event = new EnteredBTSArea(id, randomPlace.getLocation().getLongitude(), randomPlace.getLocation().getLatitude(), LocalDateTime.now());
            serializeAndSend(event, producer, topicName, isDelayed ? 2 : 0);

            //Normal events
            for (int i = 0; i < 10; i++) {
                event = createRandomBTSEvent(id);
                serializeAndSend(event, producer, topicName, isDelayed ? 2 : 0);
            }

            // last location
            randomPlace = Places.RandomEntryPlace();
            event = new LeftBTSArea(id, randomPlace.getLocation().getLongitude(), randomPlace.getLocation().getLatitude(), LocalDateTime.now());
            serializeAndSend(event, producer, topicName, isDelayed ? 2 : 0);

            // exit event
            event = new UserExited(id);
            serializeAndSend(event, producer, topicName, isDelayed ? 2 : 0);

            producer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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

    static BTSEvent createRandomBTSEvent(String id){
        Place randomPlace = Places.RandomPlace();
        Random random = new Random();
        switch (random.nextInt(5)){
            case 0:
                return new PhoneCall(id,randomPlace.getLocation().getLongitude(),randomPlace.getLocation().getLatitude(),LocalDateTime.now());
            case 1:
                return new Sms(id,randomPlace.getLocation().getLongitude(),randomPlace.getLocation().getLatitude(),LocalDateTime.now());
        }
        return null;
    }

    static void serializeAndSend(BTSEvent event, Producer<String, byte[]> producer, String topicName,int sleepTime ) throws InterruptedException, IOException {
        byte[] serializedEvent = serializeBTSEvent(event);
        producer.send(new ProducerRecord<String, byte[]>(topicName,
                event.getClass().toString(), serializedEvent));
        System.out.println(event.getId() + ": message sent successfully");
        TimeUnit.SECONDS.sleep(sleepTime);
    }


}

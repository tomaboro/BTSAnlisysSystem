package EventProducer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        KafkaEventProducer producer = new KafkaEventProducer("event-topic");
        producer.start();
    }
}

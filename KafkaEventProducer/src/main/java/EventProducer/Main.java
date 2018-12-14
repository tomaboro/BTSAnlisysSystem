package EventProducer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        while(true) {
            KafkaEventProducer producer = new KafkaEventProducer("event-topic");
            new Thread(producer).start();
            Thread.sleep(2000);
        }
    }
}

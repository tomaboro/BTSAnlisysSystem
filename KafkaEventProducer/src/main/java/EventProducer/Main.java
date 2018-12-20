package EventProducer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0) {
            System.out.println(System.currentTimeMillis());
            for (int i = 0; i < Integer.parseInt(args[0]) / 10; i++) {
                KafkaEventProducer producer = new KafkaEventProducer("event-topic", true);
                new Thread(producer).start();
            }
        } else {
            while (true) {
                KafkaEventProducer producer = new KafkaEventProducer("event-topic", true);
                new Thread(producer).start();
                Thread.sleep(1000);
            }
        }
    }
}

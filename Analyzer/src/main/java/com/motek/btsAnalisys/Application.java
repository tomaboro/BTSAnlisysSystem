package com.motek.btsAnalisys;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.motek.btsAnalisys.actors.manager.ManagingActor;
import com.motek.btsAnalisys.kafka.KafkaEventConsumer;

public class Application {
    private static final String managingAgentID = "managing";

    public void start(){
        ActorSystem system = ActorSystem.create("BTS-data-analisys-system");
        ActorRef managingAgent = system.actorOf(ManagingActor.props(),managingAgentID);

        KafkaEventConsumer kafkaEventConsumer = new KafkaEventConsumer(managingAgent);
        try {
            kafkaEventConsumer.runEventConsumer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.motek.btsAnalisys;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Source;
import com.motek.btsAnalisys.BTSEvents.BTSEvent;
import com.motek.btsAnalisys.BTSEvents.SomeBTSEvent;
import com.motek.btsAnalisys.BTSEvents.UserEntered;
import com.motek.btsAnalisys.BTSEvents.UserLeft;
import com.motek.btsAnalisys.actors.event.EventActor;
import com.motek.btsAnalisys.actors.manager.ManagingActor;
import com.motek.btsAnalisys.actors.questionary.QuestionaryActor;
import com.motek.btsAnalisys.eventEmitters.DefaultEventEmitter;
import com.motek.btsAnalisys.eventEmitters.EntranceEventEmitter;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static final String questionaryAgentID = "questionary";
    public static final String managingAgentID = "managing";
    public static final String eventAgentID = "event";

    public void start(){
        ActorSystem system = ActorSystem.create("BTS-data-analisys-system");

        ActorRef questionaryAgent = system.actorOf(QuestionaryActor.props(),questionaryAgentID);
        ActorRef eventAgent = system.actorOf(EventActor.props(),eventAgentID);
        ActorRef managingAgent = system.actorOf(ManagingActor.props(questionaryAgent),managingAgentID);

        final String id = "x12345";
        List<BTSEvent> events = Arrays.asList(new UserEntered(id), new SomeBTSEvent(id), new SomeBTSEvent(id), new UserLeft(id));
        Source<BTSEvent, NotUsed> source = Source.from(events);

        KafkaEventConsumer kafkaEventConsumer = new KafkaEventConsumer(eventAgent);
        try {
            kafkaEventConsumer.runEventConsumer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ActorMaterializer materializer = ActorMaterializer.create(system);
        //DefaultEventEmitter emitter1 = new DefaultEventEmitter(source, materializer, eventAgent);
        //EntranceEventEmitter emitter2 = new EntranceEventEmitter(source, materializer, managingAgent);

        //emitter2.start();
        //emitter1.start();

        //managingAgent.tell(new CreateAgent(id), ActorRef.noSender());
        //eventAgent.tell(new PassEvent(new SomeBTSEvent(), id), ActorRef.noSender());
        //eventAgent.tell(new PassEvent(new SomeBTSEvent(), id), ActorRef.noSender());
        //eventAgent.tell(new PassEvent(new SomeBTSEvent(), id), ActorRef.noSender());
        //managingAgent.tell(new KillAgent(id), ActorRef.noSender());

    }
}

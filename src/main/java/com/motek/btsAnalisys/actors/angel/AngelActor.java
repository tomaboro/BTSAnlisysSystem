package com.motek.btsAnalisys.actors.angel;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.angel.commands.BTSEvent;
import com.motek.btsAnalisys.actors.angel.commands.KillYourself;
import com.motek.btsAnalisys.actors.questionary.commands.PrepareQuestionary;

import java.util.ArrayList;
import java.util.List;

public class AngelActor extends AbstractActor {
    private String id;
    private List<BTSEvent> events = new ArrayList<>();
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public AngelActor(String id) {
        this.id = id;
    }

    @Override
    public void preStart() {
        log.info("Angel[" + id + "] actor started.");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(BTSEvent.class, event -> {
                    log.info("Received new event: " + event.toString());
                    events.add(event);
                })
                .match(KillYourself.class, kill -> {
                    log.info("Sending message to questionary and killing myself.");
                    kill.getQuestionaryAgent().tell(new PrepareQuestionary(events), ActorRef.noSender());
                })
                .build();
    }

    public static Props props(String id) {
        return Props.create(AngelActor.class, id);
    }
}

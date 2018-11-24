package com.motek.btsAnalisys.actors.questionary;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.questionary.commands.PrepareQuestionary;

public class QuestionaryActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        log.info("Questionary actor started.");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PrepareQuestionary.class, request -> {
                    log.info("Performing analysis");
                })
                .build();
    }

    public static Props props() {
        return Props.create(QuestionaryActor.class);
    }
}

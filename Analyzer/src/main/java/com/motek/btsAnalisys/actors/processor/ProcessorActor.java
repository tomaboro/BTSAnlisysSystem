package com.motek.btsAnalisys.actors.processor;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import com.motek.btsAnalisys.actors.processor.command.ProcessEvents;
import com.motek.btsAnalisys.actors.questionary.QuestionaryActor;

public class ProcessorActor extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ProcessEvents.class, process -> {
            //TODO: Implement processing data
        }).build();
    }

    public static Props props() {
        return Props.create(QuestionaryActor.class);
    }

}

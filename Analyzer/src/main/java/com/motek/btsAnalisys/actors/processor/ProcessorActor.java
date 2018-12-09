package com.motek.btsAnalisys.actors.processor;

import akka.actor.AbstractLoggingActor;
import com.motek.btsAnalisys.actors.processor.command.ProcessEvents;

public class ProcessorActor extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ProcessEvents.class, process -> {
            //TODO: Implement processing data
        }).build();
    }

}

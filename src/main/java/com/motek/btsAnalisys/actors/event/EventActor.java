package com.motek.btsAnalisys.actors.event;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.manager.ManagingActor;

public class EventActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        log.info("Event actor started.");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }

    public static Props props(String id) {
        return Props.create(EventActor.class);
    }
}

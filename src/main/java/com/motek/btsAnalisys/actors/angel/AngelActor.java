package com.motek.btsAnalisys.actors.angel;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.manager.ManagingActor;

public class AngelActor extends AbstractActor {
    private String id;
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
                .build();
    }

    public static Props props(String id) {
        return Props.create(ManagingActor.class, id);
    }
}

package com.motek.btsAnalisys.actors.event;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import com.motek.btsAnalisys.actors.angel.AngelActor;
import com.motek.btsAnalisys.actors.event.commands.DecodeEvent;

public class EventActor extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(DecodeEvent.class, decodeEvent -> {
            //TODO: Implement decoding
        }).build();
    }

    public static Props props() {
        return Props.create(EventActor.class);
    }
}

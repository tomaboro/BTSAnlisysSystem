package com.motek.btsAnalisys.actors.event;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import com.motek.btsAnalisys.actors.event.commands.DecodeEvent;
import utils.Place;
import utils.SimpleLocation;

import java.util.Map;

public class EventActor extends AbstractLoggingActor {
    private Map<SimpleLocation, Place> hotels;
    private Map<SimpleLocation, Place> monuments;


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

package com.motek.btsAnalisys.actors.event;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.event.commands.DecodeEvent;
import com.motek.btsAnalisys.actors.event.response.DecodedEvent;
import utils.Place;
import utils.Places;
import utils.ProcessedEvent;
import utils.SimpleLocation;

public class EventActor extends AbstractLoggingActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        log.info("Event actor started.");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(DecodeEvent.class, decodeEvent -> {
            log.info("Decoding event: " + decodeEvent.getEvent().toString());
            SimpleLocation location = new SimpleLocation((long) decodeEvent.getEvent().getLatitude(),(long) decodeEvent.getEvent().getLongitude());
            Place place;
            if((place = Places.airports.get(location)) != null) { }
            else if((place = Places.trainStations.get(location)) != null) { }
            else if((place = Places.busStations.get(location)) != null) { }
            else if((place = Places.hotels.get(location)) != null) { }
            else if((place = Places.hostels.get(location)) != null) { }
            else {
                place = new Place(location, Place.LocationType.unknown, "");
            }
            getSender().tell(new DecodedEvent(new ProcessedEvent(place,decodeEvent.getEvent().getLocalDateTime())),getSelf());
        }).build();
    }

    public static Props props() {
        return Props.create(EventActor.class);
    }
}

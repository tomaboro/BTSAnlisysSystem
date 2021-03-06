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
    public void preStart() { }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(DecodeEvent.class, decodeEvent -> {
            SimpleLocation location = new SimpleLocation((long) decodeEvent.getEvent().getLatitude(),(long) decodeEvent.getEvent().getLongitude());
            Place place;
            if((place = Places.airports.stream().filter(x -> x.getLocation().equals(location)).findFirst().orElse(null)) != null) { }
            else if((place = Places.trainStations.stream().filter(x -> x.getLocation().equals(location)).findFirst().orElse(null)) != null) { }
            else if((place = Places.busStations.stream().filter(x -> x.getLocation().equals(location)).findFirst().orElse(null)) != null) { }
            else if((place = Places.hotels.stream().filter(x -> x.getLocation().equals(location)).findFirst().orElse(null)) != null) { }
            else if((place = Places.hostels.stream().filter(x -> x.getLocation().equals(location)).findFirst().orElse(null)) != null) { }
            else {
                place = new Place(location, Place.LocationType.unknown, "");
            }
            log.info("E, " + decodeEvent.getEvent().toString() + ", " + place.toString() + ";");
            getSender().tell(new DecodedEvent(new ProcessedEvent(place,decodeEvent.getEvent().getLocalDateTime())),getSelf());
        }).build();
    }

    public static Props props() {
        return Props.create(EventActor.class);
    }
}

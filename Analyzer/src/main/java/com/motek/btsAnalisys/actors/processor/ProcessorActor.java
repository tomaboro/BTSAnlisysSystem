package com.motek.btsAnalisys.actors.processor;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.motek.btsAnalisys.actors.event.commands.DecodeEvent;
import com.motek.btsAnalisys.actors.event.response.DecodedEvent;
import com.motek.btsAnalisys.actors.processor.command.ProcessEvents;
import com.motek.btsAnalisys.actors.processor.response.EventsProcessed;
import com.motek.btsAnalisys.actors.questionary.QuestionaryActor;
import utils.Place;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ProcessorActor extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ProcessEvents.class, command -> {
            List<Place> locations = command.getEvents().stream().map(btsEvent -> {
                final Timeout timeout = new Timeout(20, TimeUnit.SECONDS);
                final Future<Object> future = Patterns.ask(command.getEventsActor(), new DecodeEvent(btsEvent), timeout);
                try {
                    final DecodedEvent result = (DecodedEvent) Await.result(future, timeout.duration());
                    return result.getLocation();
                } catch (Exception e) {
                    return new Place.ErrorPlace();
                }
            }).collect(Collectors.toList());
            getSender().tell(new EventsProcessed(locations), getSelf());
        }).build();
    }

    public static Props props() {
        return Props.create(QuestionaryActor.class);
    }

}

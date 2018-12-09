package com.motek.btsAnalisys.actors.angel;

import BTSEvents.BTSEvent;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.angel.commands.AddEvent;
import com.motek.btsAnalisys.actors.angel.commands.KillYourself;
import com.motek.btsAnalisys.actors.processor.ProcessorActor;
import com.motek.btsAnalisys.actors.processor.command.ProcessEvents;
import com.motek.btsAnalisys.actors.processor.response.EventsProcessed;
import com.motek.btsAnalisys.actors.questionary.commands.PrepareQuestionary;

import java.util.ArrayList;
import java.util.List;

public class AngelActor extends AbstractActor {
    private String id;
    private List<BTSEvent> events = new ArrayList<>();
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef processorActor = null;
    private ActorRef questionaryActor = null;
    private ActorRef eventsActor = null;

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
                .match(AddEvent.class, addEvent -> {
                    log.info("Received new event: " + addEvent.getEvent().toString());
                    events.add(addEvent.getEvent());
                })
                .match(KillYourself.class, kill -> {
                    log.info("Started processing data.");
                    questionaryActor = kill.getQuestionaryActor();
                    processorActor = getContext().actorOf(ProcessorActor.props());
                    processorActor.tell(new ProcessEvents(events,kill.getEventsActor()),getSelf());
                })
                .match(EventsProcessed.class, processedEvents -> {
                    if(questionaryActor != null) {
                        questionaryActor.tell(new PrepareQuestionary(processedEvents.getLocations()), getSelf());
                        context().self().tell(PoisonPill.getInstance(), ActorRef.noSender());
                    }
                })
                .build();
    }

    public static Props props(String id) {
        return Props.create(AngelActor.class, id);
    }
}

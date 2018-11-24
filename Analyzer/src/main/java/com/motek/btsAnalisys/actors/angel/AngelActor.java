package com.motek.btsAnalisys.actors.angel;

import BTSEvents.BTSEvent;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.angel.commands.CommitSuacide;
import com.motek.btsAnalisys.actors.angel.commands.KillYourself;
import com.motek.btsAnalisys.actors.questionary.commands.PrepareQuestionary;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AngelActor extends AbstractActor {
    private String id;
    private List<BTSEvent> events = new ArrayList<>();
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
                .match(BTSEvent.class, event -> {
                    log.info("Received new event: " + event.toString());
                    events.add(event);
                })
                .match(KillYourself.class, kill -> {
                    context().system().scheduler().scheduleOnce(Duration.ofSeconds(5),
                            () -> context().self().tell(new CommitSuacide(kill.getQuestionaryAgent()), ActorRef.noSender()), context().dispatcher());
                })
                .match(CommitSuacide.class, x -> {
                    log.info("Sending message to questionary and killing myself.");
                    x.getQuestionaryAgent().tell(new PrepareQuestionary(events), ActorRef.noSender());
                    context().self().tell(PoisonPill.getInstance(), ActorRef.noSender());
                })
                .build();
    }

    public static Props props(String id) {
        return Props.create(AngelActor.class, id);
    }
}

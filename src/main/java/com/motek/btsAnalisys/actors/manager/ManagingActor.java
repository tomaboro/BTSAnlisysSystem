package com.motek.btsAnalisys.actors.manager;
import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.motek.btsAnalisys.actors.manager.commands.CreateAgent;
import com.motek.btsAnalisys.actors.manager.commands.KillAgent;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ManagingActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        log.info("Managing actor started.");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }

    public static Props props() {
        return Props.create(ManagingActor.class);
    }
}

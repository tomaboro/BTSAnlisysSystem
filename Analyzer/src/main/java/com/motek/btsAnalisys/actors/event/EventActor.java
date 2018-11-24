package com.motek.btsAnalisys.actors.event;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.dispatch.OnSuccess;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.Application;
import com.motek.btsAnalisys.actors.event.command.PassEvent;
import com.motek.btsAnalisys.actors.event.command.PassEventRetry;
import scala.concurrent.duration.FiniteDuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class EventActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        log.info("Event actor started.");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PassEvent.class, passEvent -> {
                    log.info("Received event " + passEvent.getEvent() + " to pass to angel[" + passEvent.getAngelID() + "]");
                    context().actorSelection("../" + Application.managingAgentID + "/" + passEvent.getAngelID())
                            .resolveOne(new FiniteDuration(2, TimeUnit.SECONDS))
                            .onComplete(new OnComplete<ActorRef>() {
                                @Override
                                public void onComplete(Throwable failure, ActorRef angel) throws Throwable {
                                    if(failure != null) {
                                        context().system().scheduler().scheduleOnce(Duration.ofSeconds(3),
                                                () -> context().self().tell(new PassEventRetry(passEvent.getEvent(), passEvent.getAngelID()), ActorRef.noSender()), context().dispatcher());
                                    } else {
                                        angel.tell(passEvent.getEvent(), ActorRef.noSender());
                                    }
                                }
                            }, context().dispatcher());
                })
                .match(PassEventRetry.class, passEvent -> {
                    context().actorSelection("../" + Application.managingAgentID + "/" + passEvent.getAngelID())
                            .resolveOne(new FiniteDuration(2, TimeUnit.SECONDS))
                            .onSuccess(new OnSuccess<ActorRef>() {
                                @Override
                                public void onSuccess(ActorRef angel) throws Throwable {
                                    angel.tell(passEvent.getEvent(), ActorRef.noSender());

                                }
                            }, context().dispatcher());
                })
                .build();
    }

    public static Props props() {
        return Props.create(EventActor.class);
    }
}

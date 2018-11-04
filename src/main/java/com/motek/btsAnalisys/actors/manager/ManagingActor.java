package com.motek.btsAnalisys.actors.manager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.angel.AngelActor;
import com.motek.btsAnalisys.actors.angel.commands.KillYourself;
import com.motek.btsAnalisys.actors.manager.commands.CreateAgent;
import com.motek.btsAnalisys.actors.manager.commands.KillAgent;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class ManagingActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef questionaryActor;

    public ManagingActor(ActorRef questionaryActor) {
        this.questionaryActor = questionaryActor;
    }

    @Override
    public void preStart() {
        log.info("Managing actor started.");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreateAgent.class, createEvent -> {
                    log.info("Received angel{" + createEvent.getId() + "] creation request");
                    context().actorSelection(createEvent.getId())
                            .resolveOne(new FiniteDuration(2, TimeUnit.SECONDS))
                            .onFailure(new OnFailure() {
                                public void onFailure(Throwable failure) {
                                    context().actorOf(AngelActor.props(createEvent.getId()), createEvent.getId());
                                }
                            }, context().dispatcher());

                })
                .match(KillAgent.class, killEvent -> {
                    log.info("Received angel[" + killEvent.getId() +  "] removal request");
                    context().actorSelection(killEvent.getId())
                            .resolveOne(new FiniteDuration(2, TimeUnit.SECONDS))
                            .onSuccess(new OnSuccess<ActorRef>() {
                                @Override
                                public void onSuccess(ActorRef angel) throws Throwable {
                                    angel.tell(new KillYourself(questionaryActor), ActorRef.noSender());
                                }
                            }, context().dispatcher());

                })
                .build();
    }

    public static Props props(ActorRef questionaryActor) {
        return Props.create(ManagingActor.class, questionaryActor);
    }
}
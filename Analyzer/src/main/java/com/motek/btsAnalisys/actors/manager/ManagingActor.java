package com.motek.btsAnalisys.actors.manager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.actors.angel.AngelActor;
import com.motek.btsAnalisys.actors.angel.commands.KillYourself;
import com.motek.btsAnalisys.actors.event.command.PassEventRetry;
import com.motek.btsAnalisys.actors.manager.commands.CreateAgent;
import com.motek.btsAnalisys.actors.manager.commands.KillAgent;
import com.motek.btsAnalisys.actors.manager.commands.RetryKill;
import scala.concurrent.duration.FiniteDuration;

import java.time.Duration;
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
                .match(RetryKill.class, retryKill -> {
                    context().actorSelection(retryKill.getId())
                            .resolveOne(new FiniteDuration(2, TimeUnit.SECONDS))
                            .onComplete(new OnComplete<ActorRef>() {
                                @Override
                                public void onComplete(Throwable failure, ActorRef angel) throws Throwable {
                                    if(failure == null ){
                                        angel.tell(new KillYourself(questionaryActor), ActorRef.noSender());
                                    }
                                }
                            }, context().dispatcher());
                })
                .match(KillAgent.class, killEvent -> {
                    log.info("Received angel[" + killEvent.getId() +  "] removal request");
                    context().actorSelection(killEvent.getId())
                            .resolveOne(new FiniteDuration(2, TimeUnit.SECONDS))
                            .onComplete(new OnComplete<ActorRef>() {
                                @Override
                                public void onComplete(Throwable failure, ActorRef angel) throws Throwable {
                                    if(failure != null ){
                                        context().system().scheduler().scheduleOnce(Duration.ofSeconds(2),
                                                () -> context().self().tell(new RetryKill(killEvent.getId()), ActorRef.noSender()), context().dispatcher());
                                    } else {
                                        angel.tell(new KillYourself(questionaryActor), ActorRef.noSender());
                                    }
                                }
                            }, context().dispatcher());
                })
                .build();
    }

    public static Props props(ActorRef questionaryActor) {
        return Props.create(ManagingActor.class, questionaryActor);
    }
}

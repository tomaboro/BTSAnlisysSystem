package com.motek.btsAnalisys.actors.manager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.dispatch.OnFailure;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.motek.btsAnalisys.Application;
import com.motek.btsAnalisys.actors.angel.AngelActor;
import com.motek.btsAnalisys.actors.angel.commands.AddEvent;
import com.motek.btsAnalisys.actors.angel.commands.KillYourself;
import com.motek.btsAnalisys.actors.event.EventActor;
import com.motek.btsAnalisys.actors.manager.commands.GetInfo;
import com.motek.btsAnalisys.actors.manager.commands.PassEvent;
import com.motek.btsAnalisys.actors.manager.commands.CreateAgent;
import com.motek.btsAnalisys.actors.manager.commands.KillAgent;
import com.motek.btsAnalisys.actors.questionary.QuestionaryActor;
import scala.concurrent.duration.FiniteDuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ManagingActor extends AbstractActor {
    public static final String questionaryAgentID = "questionary";
    public static final String eventAgentID = "event";
    public static final String managingAgentID = "managing";


    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef questionaryActor;
    private ActorRef eventsActor;

    @Override
    public void preStart() {
        log.info("Managing actor started.");
        questionaryActor = getContext().actorOf(QuestionaryActor.props(),questionaryAgentID);
        eventsActor = getContext().actorOf(EventActor.props(),eventAgentID);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GetInfo.class, getInfo -> {
                    getSender().tell(questionaryActor,getSelf());
                })
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
                .match(PassEvent.class, passEvent -> {
                    log.info("Received event " + passEvent.getEvent() + " to pass to angel[" + passEvent.getAngelID() + "]");
                    context().actorSelection("../" + managingAgentID + "/" + passEvent.getAngelID())
                            .resolveOne(new FiniteDuration(2, TimeUnit.SECONDS))
                            .onComplete(new OnComplete<ActorRef>() {
                                @Override
                                public void onComplete(Throwable failure, ActorRef angel) throws Throwable {
                                        angel.tell(new AddEvent(passEvent.getEvent()), ActorRef.noSender());
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
                                        angel.tell(new KillYourself(questionaryActor,eventsActor), ActorRef.noSender());
                                }
                            }, context().dispatcher());
                })
                .build();
    }

    public static Props props() {
        return Props.create(ManagingActor.class);
    }
}

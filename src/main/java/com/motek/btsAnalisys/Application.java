package com.motek.btsAnalisys;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.motek.btsAnalisys.actors.angel.commands.SomeBTSEvent;
import com.motek.btsAnalisys.actors.event.EventActor;
import com.motek.btsAnalisys.actors.event.command.PassEvent;
import com.motek.btsAnalisys.actors.manager.ManagingActor;
import com.motek.btsAnalisys.actors.manager.commands.CreateAgent;
import com.motek.btsAnalisys.actors.manager.commands.KillAgent;
import com.motek.btsAnalisys.actors.questionary.QuestionaryActor;

import java.util.concurrent.TimeUnit;

public class Application {
    public static final String questionaryAgentID = "questionary";
    public static final String managingAgentID = "managing";
    public static final String eventAgentID = "event";

    public void start(){
        ActorSystem system = ActorSystem.create("BTS-data-analisys-system");

        ActorRef questionaryAgent = system.actorOf(QuestionaryActor.props(),questionaryAgentID);
        ActorRef eventAgent = system.actorOf(EventActor.props(),eventAgentID);
        ActorRef managingAgent = system.actorOf(ManagingActor.props(questionaryAgent),managingAgentID);

        final String id = "x12345";
        managingAgent.tell(new CreateAgent(id), ActorRef.noSender());
        eventAgent.tell(new PassEvent(new SomeBTSEvent(), id), ActorRef.noSender());
        eventAgent.tell(new PassEvent(new SomeBTSEvent(), id), ActorRef.noSender());
        eventAgent.tell(new PassEvent(new SomeBTSEvent(), id), ActorRef.noSender());
        managingAgent.tell(new KillAgent(id), ActorRef.noSender());

    }
}

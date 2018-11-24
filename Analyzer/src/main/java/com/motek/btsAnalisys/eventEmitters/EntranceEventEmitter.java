package com.motek.btsAnalisys.eventEmitters;

import BTSEvents.BTSEvent;
import BTSEvents.UserEntered;
import BTSEvents.UserLeft;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.stream.*;
import akka.stream.javadsl.*;
import com.motek.btsAnalisys.actors.manager.commands.CreateAgent;
import com.motek.btsAnalisys.actors.manager.commands.KillAgent;

public class EntranceEventEmitter {
    private Source<BTSEvent, NotUsed> source;
    private ActorMaterializer materializer;
    private ActorRef managementAgent;

    public EntranceEventEmitter(Source<BTSEvent, NotUsed> source, ActorMaterializer materializer, ActorRef managementAgent) {
        this.source = source;
        this.materializer = materializer;
        this.managementAgent = managementAgent;
    }

    public void start() {
        source.filter(event -> event instanceof UserEntered || event instanceof UserLeft)
                .runForeach(event -> {
                    if(event instanceof UserEntered) {
                        UserEntered tmp = (UserEntered) event;
                        managementAgent.tell(new CreateAgent(tmp.getId()), ActorRef.noSender());
                    } else {
                        UserLeft tmp = (UserLeft) event;
                        managementAgent.tell(new KillAgent(tmp.getId()), ActorRef.noSender());
                    }
        }, materializer);
    }

}

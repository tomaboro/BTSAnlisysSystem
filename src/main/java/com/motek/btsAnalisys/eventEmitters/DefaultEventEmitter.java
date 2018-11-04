package com.motek.btsAnalisys.eventEmitters;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Source;
import com.motek.btsAnalisys.BTSEvents.BTSEvent;
import com.motek.btsAnalisys.BTSEvents.UserEntered;
import com.motek.btsAnalisys.BTSEvents.UserLeft;
import com.motek.btsAnalisys.actors.event.command.PassEvent;

public class DefaultEventEmitter {
    private Source<BTSEvent, NotUsed> source;
    private ActorMaterializer materializer;
    private ActorRef eventAgent;

    public DefaultEventEmitter(Source<BTSEvent, NotUsed> source, ActorMaterializer materializer, ActorRef eventAgent) {
        this.source = source;
        this.materializer = materializer;
        this.eventAgent = eventAgent;
    }

    public void start() {
        source.filter(event -> !(event instanceof UserEntered || event instanceof UserLeft))
                .runForeach(event -> {
                    eventAgent.tell(new PassEvent(event,event.getId()), ActorRef.noSender());
                }, materializer);
    }
}

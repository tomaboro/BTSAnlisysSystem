package com.motek.btsAnalisys.actors.angel.commands;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KillYourself {
    private final ActorRef questionaryActor;
    private final ActorRef eventsActor;
}

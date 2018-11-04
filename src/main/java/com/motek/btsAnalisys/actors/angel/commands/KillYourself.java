package com.motek.btsAnalisys.actors.angel.commands;

import akka.actor.ActorRef;

public class KillYourself {
    private final ActorRef questionaryAgent;

    public KillYourself(ActorRef questionaryAgent) {
        this.questionaryAgent = questionaryAgent;
    }

    public ActorRef getQuestionaryAgent() {
        return questionaryAgent;
    }
}

package com.motek.btsAnalisys.actors.angel.commands;

import akka.actor.ActorRef;

public class CommitSuacide {
    private final ActorRef questionaryAgent;

    public CommitSuacide(ActorRef questionaryAgent) {
        this.questionaryAgent = questionaryAgent;
    }

    public ActorRef getQuestionaryAgent() {
        return questionaryAgent;
    }
}

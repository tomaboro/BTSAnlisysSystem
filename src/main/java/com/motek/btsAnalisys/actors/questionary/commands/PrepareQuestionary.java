package com.motek.btsAnalisys.actors.questionary.commands;

import com.motek.btsAnalisys.actors.angel.commands.BTSEvent;

import java.util.List;

public class PrepareQuestionary {
    private final List<BTSEvent> events;

    public PrepareQuestionary(List<BTSEvent> events) {
        this.events = events;
    }

    public List<BTSEvent> getEvents() {
        return events;
    }
}

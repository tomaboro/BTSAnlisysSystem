package com.motek.btsAnalisys.actors.questionary.commands;

import BTSEvents.BTSEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PrepareQuestionary {
    private final List<BTSEvent> events;
}

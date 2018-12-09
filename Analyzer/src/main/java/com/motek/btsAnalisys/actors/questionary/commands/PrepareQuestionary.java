package com.motek.btsAnalisys.actors.questionary.commands;

import utils.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.ProcessedEvent;

import java.util.List;

@AllArgsConstructor
@Getter
public class PrepareQuestionary {
    private final List<ProcessedEvent> events;
}

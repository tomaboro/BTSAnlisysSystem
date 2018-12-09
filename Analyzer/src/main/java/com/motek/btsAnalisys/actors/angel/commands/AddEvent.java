package com.motek.btsAnalisys.actors.angel.commands;

import BTSEvents.BTSEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddEvent {
    private BTSEvent event;
}

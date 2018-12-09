package com.motek.btsAnalisys.actors.manager.commands;

import BTSEvents.BTSEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PassEvent {
    final BTSEvent event;
    final String angelID;
}

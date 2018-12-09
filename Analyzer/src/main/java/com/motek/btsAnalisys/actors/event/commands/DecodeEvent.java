package com.motek.btsAnalisys.actors.event.commands;

import BTSEvents.BTSEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DecodeEvent {
    private BTSEvent event;
}

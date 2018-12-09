package com.motek.btsAnalisys.actors.processor.command;

import BTSEvents.BTSEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProcessEvents {
    private List<BTSEvent> events;
}

package com.motek.btsAnalisys.actors.processor.response;

import utils.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.ProcessedEvent;

import java.util.List;

@AllArgsConstructor
@Getter
public class EventsProcessed {
    List<ProcessedEvent> events;

    public void addLocation(ProcessedEvent event) {
        events.add(event);
    }
}

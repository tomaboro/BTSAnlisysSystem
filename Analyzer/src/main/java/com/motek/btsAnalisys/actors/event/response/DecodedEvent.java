package com.motek.btsAnalisys.actors.event.response;

import utils.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.ProcessedEvent;

@AllArgsConstructor
@Getter
public class DecodedEvent {
    private ProcessedEvent event;
}

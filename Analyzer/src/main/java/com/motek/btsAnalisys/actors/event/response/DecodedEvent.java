package com.motek.btsAnalisys.actors.event.response;

import utils.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DecodedEvent {
    Place location;
}

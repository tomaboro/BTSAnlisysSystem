package com.motek.btsAnalisys.actors.processor.response;

import utils.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class EventsProcessed {
    List<Place> locations;

    public void addLocation(Place location) {
        locations.add(location);
    }
}

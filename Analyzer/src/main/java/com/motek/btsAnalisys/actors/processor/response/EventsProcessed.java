package com.motek.btsAnalisys.actors.processor.response;

import com.motek.btsAnalisys.utils.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class EventsProcessed {
    List<Location> locations;

    public void addLocation(Location location) {
        locations.add(location);
    }
}

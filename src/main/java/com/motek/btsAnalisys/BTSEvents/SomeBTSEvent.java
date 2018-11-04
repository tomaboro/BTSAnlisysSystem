package com.motek.btsAnalisys.BTSEvents;

import com.motek.btsAnalisys.BTSEvents.BTSEvent;

public class SomeBTSEvent implements BTSEvent {
    private final String id;

    public SomeBTSEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

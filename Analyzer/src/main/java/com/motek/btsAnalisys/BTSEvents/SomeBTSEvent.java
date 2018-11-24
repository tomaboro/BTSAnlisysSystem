package com.motek.btsAnalisys.BTSEvents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.motek.btsAnalisys.BTSEvents.BTSEvent;

import java.io.Serializable;

public class SomeBTSEvent implements BTSEvent, Serializable {
    private final String id;

    @JsonCreator
    public SomeBTSEvent(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

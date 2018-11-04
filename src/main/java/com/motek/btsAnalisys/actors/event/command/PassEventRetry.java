package com.motek.btsAnalisys.actors.event.command;

import com.motek.btsAnalisys.actors.angel.commands.BTSEvent;

public class PassEventRetry {
    final BTSEvent event;
    final String angelID;

    public PassEventRetry(BTSEvent event, String angelID) {
        this.event = event;
        this.angelID = angelID;
    }

    public BTSEvent getEvent() {
        return event;
    }

    public String getAngelID() {
        return angelID;
    }
}

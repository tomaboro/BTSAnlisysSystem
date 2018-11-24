package com.motek.btsAnalisys.BTSEvents;

public class UserEntered implements BTSEvent {
    private final String id;

    public UserEntered(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package com.motek.btsAnalisys.actors.manager.commands;

public class RetryKill {
    private final String id;

    public RetryKill(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

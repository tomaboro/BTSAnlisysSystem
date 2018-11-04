package com.motek.btsAnalisys.actors.manager.commands;

public class KillAgent {
    private final String id;

    public KillAgent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package com.motek.btsAnalisys.actors.manager.commands;

public class CreateAgent {
    private final String id;

    public CreateAgent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

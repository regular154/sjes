package com.github.regular154.sjes.aggregate;

public abstract class InitialCommand {
    private final String id;

    protected InitialCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

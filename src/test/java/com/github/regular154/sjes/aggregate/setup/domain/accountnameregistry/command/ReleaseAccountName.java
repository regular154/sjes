package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command;

public class ReleaseAccountName extends AccountNameRegistryCommand {

    private static final String TYPE = "ReleaseAccountName";

    private final String name;

    public ReleaseAccountName(String id, String aggregateId, String name) {
        super(id, TYPE, aggregateId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

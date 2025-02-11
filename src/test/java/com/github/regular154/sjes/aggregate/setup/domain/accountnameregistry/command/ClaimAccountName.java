package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command;

public class ClaimAccountName extends AccountNameRegistryCommand {

    private static final String TYPE = "ClaimAccountName";

    private final String name;

    public ClaimAccountName(String id, String aggregateId, String name) {
        super(id, TYPE, aggregateId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

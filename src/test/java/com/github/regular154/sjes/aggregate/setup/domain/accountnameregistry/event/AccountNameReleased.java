package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event;

public class AccountNameReleased extends AccountNameRegistryEvent {

    private static final String TYPE = "AccountNameReleased";
    private final String name;

    public AccountNameReleased(String id, String aggregateId, long sequence, String name) {
        super(id, TYPE, aggregateId, sequence);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

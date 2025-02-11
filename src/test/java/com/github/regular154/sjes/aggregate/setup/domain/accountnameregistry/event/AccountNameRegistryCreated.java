package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event;

public class AccountNameRegistryCreated extends AccountNameRegistryEvent {

    private static final String TYPE = "AccountNameRegistryCreated";

    public AccountNameRegistryCreated(String id, String aggregateId, long sequence) {
        super(id, TYPE, aggregateId, sequence);
    }
}

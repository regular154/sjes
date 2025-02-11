package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event;

import com.github.regular154.sjes.aggregate.Event;

public abstract class AccountNameRegistryEvent extends Event {

    public static final String AGGREGATE_TYPE = "AccountNameRegistry";

    public AccountNameRegistryEvent(String id, String type, String aggregateId, long sequence) {
        super(id, type, aggregateId, AGGREGATE_TYPE, sequence);
    }
}

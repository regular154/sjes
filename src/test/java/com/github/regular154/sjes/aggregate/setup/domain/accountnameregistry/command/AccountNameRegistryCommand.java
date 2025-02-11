package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command;

import com.github.regular154.sjes.aggregate.Command;

public abstract class AccountNameRegistryCommand extends Command {

    private static final String AGGREGATE_TYPE = "AccountNameRegistry";

    protected AccountNameRegistryCommand(String id, String type, String aggregateId) {
        super(id, type, aggregateId, AGGREGATE_TYPE);
    }
}

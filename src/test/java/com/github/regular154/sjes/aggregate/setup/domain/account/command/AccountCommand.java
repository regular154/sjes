package com.github.regular154.sjes.aggregate.setup.domain.account.command;

import com.github.regular154.sjes.aggregate.Command;

public abstract class AccountCommand extends Command {

    private static final String AGGREGATE_TYPE = "Account";

    protected AccountCommand(String id, String type, String aggregateId) {
        super(id, type, aggregateId, AGGREGATE_TYPE);
    }
}

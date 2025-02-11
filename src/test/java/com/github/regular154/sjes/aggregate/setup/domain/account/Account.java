package com.github.regular154.sjes.aggregate.setup.domain.account;

import com.github.regular154.sjes.aggregate.Aggregate;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.AccountCommand;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.AccountInitialCommand;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountCreated;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountEvent;
import com.github.regular154.sjes.aggregate.setup.domain.account.state.AccountState;
import com.github.regular154.sjes.aggregate.setup.domain.account.state.Pending;

import java.util.List;
import java.util.UUID;

public class Account extends Aggregate<AccountEvent, AccountCommand, AccountState, AccountInitialCommand> {

    public Account(AccountInitialCommand command) {
        super(command);
    }

    public Account(List<AccountEvent> events) {
        super(events);
    }

    public String getName() {
        return this.state.getName();
    }

    @Override
    protected AccountEvent getInitialEvent(AccountInitialCommand command, long sequence) {
        return new AccountCreated(UUID.randomUUID().toString(), command.getId(), sequence, command.getName(), 0);
    }

    @Override
    protected AccountState init(AccountEvent event) {
        if (event instanceof AccountCreated accountCreated) {
            return create(accountCreated);
        } else {
            throw new RuntimeException("Unexpected event");
        }
    }

    private Pending create(AccountCreated event) {
        return new Pending(event.getName(), 0);
    }
}

package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry;

import com.github.regular154.sjes.aggregate.Aggregate;
import com.github.regular154.sjes.aggregate.Event;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.AccountNameRegistryCommand;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.AccountNameRegistryInitialCommand;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event.AccountNameRegistryCreated;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event.AccountNameRegistryEvent;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.state.AccountNameRegistryState;

import java.util.List;
import java.util.UUID;

public class AccountNameRegistry extends Aggregate<AccountNameRegistryEvent, AccountNameRegistryCommand, AccountNameRegistryState, AccountNameRegistryInitialCommand> {

    public AccountNameRegistry(AccountNameRegistryInitialCommand command) {
        super(command);
    }

    public AccountNameRegistry(List<? extends Event> events) {
        super(events);
    }

    @Override
    protected AccountNameRegistryEvent getInitialEvent(AccountNameRegistryInitialCommand command, long sequence) {
        return new AccountNameRegistryCreated(UUID.randomUUID().toString(), command.getId(), sequence);
    }

    @Override
    protected AccountNameRegistryState init(AccountNameRegistryEvent event) {
        return new AccountNameRegistryState();
    }
}

package com.github.regular154.sjes.aggregate.setup.domain;

import com.github.regular154.sjes.aggregate.DomainException;
import com.github.regular154.sjes.aggregate.Event;
import com.github.regular154.sjes.aggregate.setup.domain.account.Account;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.AccountInitialCommand;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.ChangeAccountName;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.Deposit;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.Withdraw;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountEvent;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.AccountNameRegistry;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.AccountNameRegistryInitialCommand;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.ClaimAccountName;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.ReleaseAccountName;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class AccountDomainService {

    private final ExtendedEventStore eventStore;

    public AccountDomainService(ExtendedEventStore eventStore) {
        this.eventStore = eventStore;
    }

    public String create(String name) throws DomainException {
        AccountNameRegistry nameRegistry = loadNameRegistry();
        nameRegistry.process(new ClaimAccountName(UUID.randomUUID().toString(), nameRegistry.getId(), name));
        Account account = new Account(new AccountInitialCommand(UUID.randomUUID().toString(), name));
        List<Event> events = Stream.concat(nameRegistry.getEvents().stream(), account.getEvents().stream()).toList();
        eventStore.save(events);
        return account.getId();
    }

    public void deposit(String id, int amount) throws DomainException {
        Account account = load(id);
        Deposit command = new Deposit(UUID.randomUUID().toString(), id, amount);
        account.process(command);
        eventStore.save(account.getEvents());
    }

    public void withdraw(String id, int amount) throws DomainException {
        Account account = load(id);
        Withdraw command = new Withdraw(UUID.randomUUID().toString(), id, amount);
        account.process(command);
        eventStore.save(account.getEvents());
    }

    public void rename(String id, String name) throws DomainException {
        AccountNameRegistry nameRegistry = loadNameRegistry();
        nameRegistry.process(new ClaimAccountName(UUID.randomUUID().toString(), nameRegistry.getId(), name));
        Account account = load(id);
        nameRegistry.process(new ReleaseAccountName(UUID.randomUUID().toString(), nameRegistry.getId(), account.getName()));
        ChangeAccountName command = new ChangeAccountName(UUID.randomUUID().toString(), id, name);
        account.process(command);
        List<Event> events = Stream.concat(nameRegistry.getEvents().stream(), account.getEvents().stream()).toList();
        eventStore.save(events);
    }

    private Account load(String id) {
        var events = eventStore.load(id);
        var castedEvents = events.stream().map(event -> (AccountEvent) event).toList();
        return new Account(castedEvents);
    }

    private AccountNameRegistry loadNameRegistry() {
        var events = eventStore.loadAccountNameRegistry();
        if (events.isEmpty()) {
            return new AccountNameRegistry(new AccountNameRegistryInitialCommand(UUID.randomUUID().toString()));
        }
        return new AccountNameRegistry(events);
    }
}

package com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.state;

import com.github.regular154.sjes.aggregate.DomainException;
import com.github.regular154.sjes.aggregate.State;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.AccountNameRegistryCommand;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.ClaimAccountName;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.command.ReleaseAccountName;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event.AccountNameClaimed;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event.AccountNameRegistryEvent;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event.AccountNameReleased;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AccountNameRegistryState extends State<AccountNameRegistryEvent, AccountNameRegistryCommand> {

    private final Set<String> nameRegistry = new HashSet<>();

    @Override
    public String getStateName() {
        return "CREATED";
    }

    @Override
    public AccountNameRegistryEvent processCommand(AccountNameRegistryCommand command, long sequence) throws DomainException {
        return switch (command) {
            case ClaimAccountName claimAccountName -> {
                if (nameRegistry.contains(claimAccountName.getName())) {
                    throw new DomainException("Name already claimed");
                }
                yield new AccountNameClaimed(
                        UUID.randomUUID().toString(),
                        claimAccountName.getAggregateId(),
                        sequence,
                        claimAccountName.getName());
            }
            case ReleaseAccountName releaseAccountName -> {
                if (!nameRegistry.contains(releaseAccountName.getName())) {
                    throw new DomainException("Name not claimed");
                }
                yield new AccountNameReleased(
                        UUID.randomUUID().toString(),
                        releaseAccountName.getAggregateId(),
                        sequence,
                        releaseAccountName.getName());
            }
            default -> throw new DomainException("Unknown command");
        };
    }

    @Override
    public State<AccountNameRegistryEvent, AccountNameRegistryCommand> applyEvent(AccountNameRegistryEvent event) {
        return switch (event) {
            case AccountNameClaimed accountNameClaimed -> {
                nameRegistry.add(accountNameClaimed.getName());
                yield this;
            }
            case AccountNameReleased accountNameReleased -> {
                nameRegistry.remove(accountNameReleased.getName());
                yield this;
            }
            default -> throw new IllegalArgumentException("Unknown event");
        };
    }
}

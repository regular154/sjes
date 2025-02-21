package com.github.regular154.sjes.aggregate.setup.domain.account.state;

import com.github.regular154.sjes.aggregate.DomainException;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.AccountCommand;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.ChangeAccountName;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.Deposit;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.Withdraw;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountCreated;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountEvent;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountFundsDeposited;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountFundsWithdrawn;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountNameChanged;

import java.util.List;
import java.util.UUID;

public class Active extends AccountState {

    public Active(String name, Integer balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String getStateName() {
        return "ACTIVE";
    }

    @Override
    public List<AccountEvent> processCommand(AccountCommand command, long sequence) throws DomainException {
        return switch (command) {
            case Deposit deposit -> {
                if (deposit.getAmount() <= 0) {
                    throw new DomainException("Deposit amount must be greater than 0");
                }
                var resultedBalance = balance + deposit.getAmount();
                yield List.of(new AccountFundsDeposited(
                        UUID.randomUUID().toString(),
                        command.getAggregateId(),
                        sequence,
                        deposit.getAmount(),
                        resultedBalance));
            }
            case Withdraw withdraw -> {
                if (withdraw.getAmount() <= 0) {
                    throw new DomainException("Deposit amount must be greater than 0");
                }
                var resultedBalance = balance - withdraw.getAmount();
                yield List.of(new AccountFundsWithdrawn(
                        UUID.randomUUID().toString(),
                        command.getAggregateId(),
                        sequence,
                        withdraw.getAmount(),
                        resultedBalance));
            }
            case ChangeAccountName changeAccountName -> List.of(new AccountNameChanged(
                    UUID.randomUUID().toString(),
                    changeAccountName.getAggregateId(),
                    sequence,
                    changeAccountName.getName()));
            default -> throw new IllegalStateException("Unexpected command");
        };
    }

    @Override
    public AccountState applyEvent(AccountEvent event) {
        try {
            return switch (event) {
                case AccountCreated ignored -> throw new DomainException("Account already initiated");
                case AccountFundsDeposited accountFundsDeposited -> {
                    this.balance = accountFundsDeposited.getBalance();
                    yield this;
                }
                case AccountFundsWithdrawn accountFundsWithdrawn -> {
                    this.balance = accountFundsWithdrawn.getBalance();
                    yield this;
                }
                case AccountNameChanged accountNameChanged -> {
                    this.name = accountNameChanged.getName();
                    yield this;
                }
                default -> throw new IllegalStateException("Unexpected event");
            };
        } catch (DomainException e) {
            throw new RuntimeException(e);
        }

    }
}

package com.github.regular154.sjes.aggregate.setup.domain.account.state;

import com.github.regular154.sjes.aggregate.DomainException;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.AccountCommand;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.ChangeAccountName;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.Deposit;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.Withdraw;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountEvent;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountFundsDeposited;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountFundsWithdrawn;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountNameChanged;

import java.util.UUID;


public class Pending extends AccountState {

    @Override
    public String getStateName() {
        return "PENDING";
    }

    public Pending(String name, Integer balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public AccountEvent processCommand(AccountCommand command, long sequence) throws DomainException {
        return switch (command) {
            case Deposit deposit -> {
                if (deposit.getAmount() <= 0) {
                    throw new DomainException("Deposit amount must be greater than 0");
                }
                var resultedBalance = balance + deposit.getAmount();
                yield new AccountFundsDeposited(
                        UUID.randomUUID().toString(),
                        command.getAggregateId(),
                        sequence,
                        deposit.getAmount(),
                        resultedBalance);
            }
            case Withdraw ignored -> throw new DomainException("Account is not active");
            case ChangeAccountName changeAccountName -> new AccountNameChanged(
                    UUID.randomUUID().toString(),
                    changeAccountName.getAggregateId(),
                    sequence,
                    changeAccountName.getName());
            default -> throw new IllegalStateException("Unexpected command");
        };
    }

    @Override
    public AccountState applyEvent(AccountEvent event) {
        try {
            return switch (event) {
                case AccountFundsDeposited accountFundsDeposited -> {
                    var balance = accountFundsDeposited.getBalance();
                    yield new Active(name, balance);
                }
                case AccountFundsWithdrawn ignored -> throw new DomainException("Account is not active");
                default -> throw new IllegalStateException("Unexpected event");
            };
        } catch (DomainException e) {
            throw new RuntimeException(e);
        }
    }
}

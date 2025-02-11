package com.github.regular154.sjes.aggregate.setup.domain.account.state;

import com.github.regular154.sjes.aggregate.State;
import com.github.regular154.sjes.aggregate.setup.domain.account.command.AccountCommand;
import com.github.regular154.sjes.aggregate.setup.domain.account.event.AccountEvent;

public abstract class AccountState extends State<AccountEvent, AccountCommand> {

    protected String name;
    protected Integer balance;

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }
}

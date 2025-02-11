package com.github.regular154.sjes.aggregate.setup.domain.account.command;

import com.github.regular154.sjes.aggregate.InitialCommand;

public class AccountInitialCommand extends InitialCommand {

    private final String name;

    public AccountInitialCommand(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

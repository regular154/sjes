package com.github.regular154.sjes.aggregate.setup.domain.account.command;

public class ChangeAccountName extends AccountCommand {

    private final String name;

    public ChangeAccountName(String id, String aggregateId, String name) {
        super(id, ChangeAccountName.class.getSimpleName(), aggregateId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

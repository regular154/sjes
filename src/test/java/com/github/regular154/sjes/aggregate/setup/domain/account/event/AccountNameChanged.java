package com.github.regular154.sjes.aggregate.setup.domain.account.event;

public class AccountNameChanged extends AccountEvent {

    private final String name;

    public AccountNameChanged(String id, String aggregateId, long sequence, String name) {
        super(id, AccountNameChanged.class.getSimpleName(), aggregateId, sequence);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.github.regular154.sjes.aggregate.setup.domain.account.event;

public class AccountCreated extends AccountEvent {
    private final String name;
    private final Integer balance;

    public AccountCreated(String id, String aggregateId, long sequence, String name, Integer balance) {
        super(id, AccountCreated.class.getSimpleName(), aggregateId, sequence);
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }
}

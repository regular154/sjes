package com.github.regular154.sjes.aggregate.setup.domain.account.command;

public class Deposit extends AccountCommand {

    private final Integer amount;

    public Deposit(String id, String aggregateId, Integer amount) {
        super(id, Deposit.class.getSimpleName(), aggregateId);
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }
}

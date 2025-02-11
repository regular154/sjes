package com.github.regular154.sjes.aggregate.setup.domain.account.command;

public class Withdraw extends AccountCommand {

    private final Integer amount;

    public Withdraw(String id, String aggregateId, Integer amount) {
        super(id, Withdraw.class.getSimpleName(), aggregateId);
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }
}

package com.github.regular154.sjes.aggregate.setup.domain.account.event;

public class AccountFundsWithdrawn extends AccountEvent {
    private final Integer amount;
    private final Integer balance;

    public AccountFundsWithdrawn(String id, String aggregateId, long sequence, Integer amount, Integer balance) {
        super(id, AccountFundsWithdrawn.class.getSimpleName(), aggregateId, sequence);
        this.amount = amount;
        this.balance = balance;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getBalance() {
        return balance;
    }
}

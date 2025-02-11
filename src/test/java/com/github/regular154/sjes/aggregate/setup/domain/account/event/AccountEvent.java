package com.github.regular154.sjes.aggregate.setup.domain.account.event;

import com.github.regular154.sjes.aggregate.Event;
import com.github.regular154.sjes.aggregate.setup.domain.account.Account;

public abstract class AccountEvent extends Event {
    public AccountEvent(String id, String type, String aggregateId, long sequence) {
        super(id, type, aggregateId, Account.class.getSimpleName(), sequence);
    }
}

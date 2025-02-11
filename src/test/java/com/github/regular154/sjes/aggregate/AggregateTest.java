package com.github.regular154.sjes.aggregate;

import com.github.regular154.sjes.aggregate.setup.domain.AccountDomainService;
import com.github.regular154.sjes.aggregate.setup.domain.ExtendedEventStore;
import com.github.regular154.sjes.aggregate.setup.infrastructure.EventStoreImpl;
import org.junit.jupiter.api.Test;

class AggregateTest {

    @Test
    void shouldCreateAccount() throws DomainException {

        // given
        ExtendedEventStore eventStore = new EventStoreImpl();
        AccountDomainService accountService = new AccountDomainService(eventStore);

        // when
        String accountId = accountService.create("Alice");
        accountService.deposit(accountId, 100);
        accountService.withdraw(accountId, 10);
        accountService.rename(accountId, "Bob");

        //then
        assert eventStore.load(accountId).size() == 4;
        assert eventStore.loadAccountNameRegistry().size() == 4;
    }

}
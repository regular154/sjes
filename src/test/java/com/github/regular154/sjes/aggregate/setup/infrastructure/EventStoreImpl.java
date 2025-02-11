package com.github.regular154.sjes.aggregate.setup.infrastructure;

import com.github.regular154.sjes.aggregate.Event;
import com.github.regular154.sjes.aggregate.EventStore;
import com.github.regular154.sjes.aggregate.setup.domain.ExtendedEventStore;
import com.github.regular154.sjes.aggregate.setup.domain.accountnameregistry.event.AccountNameRegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class EventStoreImpl implements ExtendedEventStore {

    List<Event> events = new ArrayList<>();

    @Override
    public List<Event> load(String aggregateId) {
        return events.stream()
                .filter(event -> event.getAggregateId().equals(aggregateId))
                .toList();
    }

    @Override
    public void save(List<? extends Event> events) {
        this.events.addAll(events);
    }

    @Override
    public List<Event> loadAccountNameRegistry() {
        return events.stream()
                .filter(event -> event.getAggregateType().equals(AccountNameRegistryEvent.AGGREGATE_TYPE))
                .toList();
    }
}

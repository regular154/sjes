package com.github.regular154.sjes.aggregate.setup.domain;

import com.github.regular154.sjes.aggregate.Event;
import com.github.regular154.sjes.aggregate.EventStore;

import java.util.List;

public interface ExtendedEventStore extends EventStore {

    List<Event> loadAccountNameRegistry();
}

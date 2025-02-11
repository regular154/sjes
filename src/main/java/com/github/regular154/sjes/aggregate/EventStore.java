package com.github.regular154.sjes.aggregate;

import java.util.List;

public interface EventStore {

    List<Event> load(String aggregateId);

    void save(List<? extends Event> events);

}

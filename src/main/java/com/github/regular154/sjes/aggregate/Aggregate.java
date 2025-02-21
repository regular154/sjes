package com.github.regular154.sjes.aggregate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Aggregate<E extends Event, C extends Command, S extends State<E, C>, I extends InitialCommand> {

    protected final String id;
    protected S state;
    protected long sequence;
    protected List<E> events;

    public List<E> getEvents() {
        return events;
    }

    public Aggregate(I command) {
        this.id = command.getId();
        this.sequence = -1;
        this.events = new ArrayList<>();
        E initialEvent = getInitialEvent(command, nextSequence());
        events.add(initialEvent);
        state = init(initialEvent);
    }

    public Aggregate(List<? extends Event> events) {
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Could not construct aggregate from empty list of events");
        }
        if (events.size() != events.stream().map(Event::getSequence).distinct().count()) {
            throw new IllegalArgumentException("Could not construct aggregate, sequence number duplicate");
        }
        List<E> castedEvents = events.stream()
                .map(e -> (E) e)
                .toList();
        this.id = castedEvents.getFirst().getId();
        this.events = new ArrayList<>();
        var sortedEvents = castedEvents.stream()
                .sorted(Comparator.comparingLong(Event::getSequence))
                .toList();
        E initialEvent = sortedEvents.getFirst();
        state = init(initialEvent);
        sortedEvents.stream()
                .skip(1)
                .forEach(this::apply);
        this.sequence = events.getLast().getSequence();
    }


    protected abstract E getInitialEvent(I command, long sequence);

    protected abstract S init(E event);
    
    private long nextSequence() {
        return ++sequence;
    }

    public String getId() {
        return id;
    }

    public void process(C command) throws DomainException {
        List<E> events = state.processCommand(command, nextSequence()).stream()
                .sorted(Comparator.comparingLong(Event::getSequence))
                .toList();
        this.sequence = events.getLast().getSequence();
        for (E event : events) {
            state = (S) state.applyEvent(event);
        }
        this.events.addAll(events);
    }

    private void apply(E event) {
        if (state == null) {
            state = init(event);
        } else {
            state = (S) state.applyEvent(event);
        }
    }

}

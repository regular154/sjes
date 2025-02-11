package com.github.regular154.sjes.aggregate;

public abstract class Event {
    private final String id;
    private final String type;
    private final String aggregateId;
    private final String aggregateType;
    private final long sequence;

    public Event(String id, String type, String aggregateId, String aggregateType, long sequence) {
        this.id = id;
        this.type = type;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.sequence = sequence;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public long getSequence() {
        return sequence;
    }

    public String getId() {
        return id;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public String getType() {
        return type;
    }

}

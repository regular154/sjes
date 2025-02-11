package com.github.regular154.sjes.aggregate;

public abstract class Command {

    private final String id;
    private final String aggregateId;
    private final String aggregateType;
    private final String type;
    private Result result;

    protected Command(String id, String type, String aggregateId, String aggregateType) {
        this.id = id;
        this.type = type;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }

    public String getId() {
        return id;
    }

    public Command success() {
        this.result = Result.SUCCESS;
        return this;
    }

    public Command failure() {
        this.result = Result.FAILURE;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public enum Result {SUCCESS, FAILURE}
}

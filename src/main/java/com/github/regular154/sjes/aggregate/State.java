package com.github.regular154.sjes.aggregate;

import java.util.List;

public abstract class State<E, C> {

    public abstract String getStateName();
    public abstract List<E> processCommand(C command, long sequence) throws DomainException;
    public abstract State<E, C> applyEvent(E event);
}

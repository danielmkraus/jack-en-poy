package org.danielmkraus.jackenpoy.domain;

public enum Shape {
    PAPER,
    ROCK,
    SCISSOR;

    MatchResult against(Shape shape) {
        throw new UnsupportedOperationException();
    }
}
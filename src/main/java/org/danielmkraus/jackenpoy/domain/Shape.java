package org.danielmkraus.jackenpoy.domain;

import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;
import static org.danielmkraus.jackenpoy.domain.MatchResult.*;

public enum Shape {
    PAPER,
    ROCK,
    SCISSOR;

    private static final Map<Map.Entry<Shape, Shape>, MatchResult> RESULTS =
            Map.ofEntries(
                    entry(entry(ROCK, ROCK), DRAW),
                    entry(entry(ROCK, SCISSOR), WIN),
                    entry(entry(ROCK, PAPER), LOOSE),
                    entry(entry(PAPER, PAPER), DRAW),
                    entry(entry(PAPER, ROCK), WIN),
                    entry(entry(PAPER, SCISSOR), LOOSE),
                    entry(entry(SCISSOR, SCISSOR), DRAW),
                    entry(entry(SCISSOR, PAPER), WIN),
                    entry(entry(SCISSOR, ROCK), LOOSE));

    MatchResult against(Shape shape) {
        Objects.requireNonNull(shape);
        return RESULTS.get(entry(this, shape));
    }
}

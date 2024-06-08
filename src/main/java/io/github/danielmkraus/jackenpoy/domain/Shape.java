package io.github.danielmkraus.jackenpoy.domain;

import static java.util.Map.entry;

import java.util.Map;
import java.util.Objects;

public enum Shape {
  PAPER,
  ROCK,
  SCISSOR;

  private static final Map<Map.Entry<Shape, Shape>, MatchResult> RESULTS =
      Map.ofEntries(
          entry(entry(ROCK, ROCK), MatchResult.DRAW),
          entry(entry(ROCK, SCISSOR), MatchResult.WIN),
          entry(entry(ROCK, PAPER), MatchResult.LOSE),
          entry(entry(PAPER, PAPER), MatchResult.DRAW),
          entry(entry(PAPER, ROCK), MatchResult.WIN),
          entry(entry(PAPER, SCISSOR), MatchResult.LOSE),
          entry(entry(SCISSOR, SCISSOR), MatchResult.DRAW),
          entry(entry(SCISSOR, PAPER), MatchResult.WIN),
          entry(entry(SCISSOR, ROCK), MatchResult.LOSE));

  MatchResult against(Shape shape) {
    Objects.requireNonNull(shape);
    return RESULTS.get(entry(this, shape));
  }
}

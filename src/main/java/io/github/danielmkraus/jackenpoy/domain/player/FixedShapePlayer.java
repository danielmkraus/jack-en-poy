package io.github.danielmkraus.jackenpoy.domain.player;

import io.github.danielmkraus.jackenpoy.domain.Shape;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FixedShapePlayer implements Player {

  @NonNull
  private final Shape shape;

  @Override
  public Shape play() {
    return shape;
  }
}

package io.github.danielmkraus.jackenpoy.domain.player;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import io.github.danielmkraus.jackenpoy.domain.Shape;

@AllArgsConstructor
public class FixedShapePlayer implements Player {
    @NonNull
    private final Shape shape;

    @Override
    public Shape play() {
        return shape;
    }
}

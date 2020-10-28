package org.danielmkraus.jackenpoy.domain;

import java.util.Objects;

public class FixedShapePlayer implements Player {
    private final Shape shape;

    public FixedShapePlayer(Shape shape) {
        Objects.requireNonNull(shape);
        this.shape = shape;
    }

    @Override
    public Shape play() {
        return shape;
    }
}

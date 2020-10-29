package org.danielmkraus.jackenpoy.domain.player;

import org.danielmkraus.jackenpoy.domain.Shape;

import java.util.Objects;
import java.util.function.Supplier;

public class RandomShapePlayer implements Player {
    private final Supplier<Integer> random;

    public RandomShapePlayer(Supplier<Integer> random) {
        Objects.requireNonNull(random);
        this.random = random;
    }

    @Override
    public Shape play() {
        var shapes = Shape.values();
        var randomIndex = Math.abs(random.get()) % shapes.length;
        return shapes[randomIndex];
    }
}

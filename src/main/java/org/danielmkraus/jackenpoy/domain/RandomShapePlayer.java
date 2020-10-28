package org.danielmkraus.jackenpoy.domain;

import java.util.Objects;
import java.util.Random;

public class RandomShapePlayer implements Player {
    private final Random random;

    public RandomShapePlayer(Random random){
        Objects.requireNonNull(random);
        this.random = random;
    }

    @Override
    public Shape play() {
        var shapes = Shape.values();
        var randomIndex = random.nextInt() % shapes.length;
        return shapes[randomIndex];
    }
}

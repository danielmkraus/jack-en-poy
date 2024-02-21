package io.github.danielmkraus.jackenpoy.domain.player;

import io.github.danielmkraus.jackenpoy.domain.Shape;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.function.Supplier;

@AllArgsConstructor
public class RandomShapePlayer implements Player {
    @NonNull
    private final Supplier<Integer> random;

    @Override
    public Shape play() {
        var shapes = Shape.values();
        var randomIndex = Math.abs(random.get()) % shapes.length;
        return shapes[randomIndex];
    }
}

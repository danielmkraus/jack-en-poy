package org.danielmkraus.jackenpoy.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.danielmkraus.jackenpoy.domain.MatchResult.*;
import static org.danielmkraus.jackenpoy.domain.Shape.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ShapeTest {

    @ParameterizedTest(name = "{0} against {1} should {2}")
    @MethodSource("shapesAgainstArguments")
    void shapes_against(Shape shape, Shape against, MatchResult result) {
        assertThat(shape.against(against)).isEqualTo(result);
    }

    @EnumSource(Shape.class)
    @ParameterizedTest(name = "{0} against null shape will throw NullPointerException")
    void against_null_shape(Shape shape) {
        assertThatNullPointerException().isThrownBy(() -> shape.against(null));
    }

    @Test
    void ensure_that_all_possibilities_are_covered() {
        var allPossibilities = Stream.of(Shape.values()).flatMap(shape -> Stream.of(Shape.values())
                .map(against -> Map.entry(shape, against)))
                .collect(Collectors.toSet());

        var mappedPossibilities = shapesAgainstArguments().stream()
                .map(arguments ->
                        Map.entry((Shape) arguments.get()[0],
                                (Shape) arguments.get()[1]))
                .collect(Collectors.toSet());

        assertThat(mappedPossibilities).containsAll(allPossibilities);
    }

    private static List<Arguments> shapesAgainstArguments(){
        return List.of(
                arguments(ROCK,ROCK,DRAW),
                arguments(ROCK,SCISSOR,WIN),
                arguments(ROCK,PAPER,LOOSE),
                arguments(PAPER,PAPER,DRAW),
                arguments(PAPER,ROCK,WIN),
                arguments(PAPER,SCISSOR,LOOSE),
                arguments(SCISSOR,SCISSOR,DRAW),
                arguments(SCISSOR,PAPER,WIN),
                arguments(SCISSOR,ROCK,LOOSE)
        );
    }
}
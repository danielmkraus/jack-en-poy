package org.danielmkraus.jackenpoy.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ShapeTest {

    @ParameterizedTest(name = "{0} against {1} should {2}")
    @CsvSource({
            "ROCK,ROCK,DRAW",
            "ROCK,SCISSOR,WIN",
            "ROCK,PAPER,LOOSE",
            "PAPER,PAPER,DRAW",
            "PAPER,ROCK,WIN",
            "PAPER,SCISSOR,LOOSE",
            "SCISSOR,SCISSOR,DRAW",
            "SCISSOR,PAPER,WIN",
            "SCISSOR,ROCK,LOOSE"})
    public void shapes_against(Shape shape, Shape against, MatchResult result) {
        assertThat(shape.against(against)).isEqualTo(result);
    }

    @EnumSource(Shape.class)
    @ParameterizedTest(name = "{0} against null shape will throw NullPointerException")
    public void against_null_shape(Shape shape) {
        assertThatNullPointerException().isThrownBy(() -> shape.against(null));
    }
}
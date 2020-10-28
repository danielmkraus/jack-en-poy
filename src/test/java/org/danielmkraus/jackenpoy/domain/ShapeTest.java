package org.danielmkraus.jackenpoy.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void assert_shapes_against(Shape shape, Shape against, MatchResult result){
        assertThat(shape.against(against)).isEqualTo(result);
    }
}
package io.github.danielmkraus.jackenpoy.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MatchTest {

    @ParameterizedTest
    @CsvSource(value = {
            "null,null,null",
            "null,ROCK,null",
            "ROCK,null,null",
            "ROCK,ROCK,DRAW"
    }, nullValues = {"null"})
    void return_score(Shape shape, Shape against, MatchResult result) {
        var match = Match.builder()
                .shape(shape)
                .against(against)
                .build();
        assertThat(match.getResult()).isEqualTo(result);
    }
}
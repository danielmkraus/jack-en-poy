package org.danielmkraus.jackenpoy.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class FixedShapePlayerTest {

    @ParameterizedTest
    @EnumSource(Shape.class)
    void fixed_shape_player_should_play_specified_shape(Shape shape){
        var player = new FixedShapePlayer(shape);
        assertThat(player.play()).isEqualTo(shape);
    }

    @Test
    void throw_null_pointer_exception_when_creates_a_fixed_shape_player_with_null(){
        assertThatNullPointerException().isThrownBy(()-> new FixedShapePlayer(null));
    }
}
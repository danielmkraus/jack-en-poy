package io.github.danielmkraus.jackenpoy.domain.player;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import io.github.danielmkraus.jackenpoy.domain.Shape;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class FixedShapePlayerTest {

  @ParameterizedTest
  @EnumSource(Shape.class)
  void fixed_shape_player_should_play_specified_shape(Shape shape) {
    var player = new FixedShapePlayer(shape);
    Assertions.assertThat(player.play()).isEqualTo(shape);
  }

  @Test
  void throw_null_pointer_exception_when_creates_a_fixed_shape_player_with_null() {
    assertThatNullPointerException().isThrownBy(() -> new FixedShapePlayer(null));
  }
}
package org.danielmkraus.jackenpoy.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomShapePlayerTest {
    @Mock
    Random random;
    @InjectMocks
    RandomShapePlayer player;

    @ParameterizedTest
    @MethodSource("randomPlayerCanGenerateAllValuesArguments")
    void random_player_can_generate_all_values(Integer index, Shape shape){
        when(random.nextInt()).thenReturn(index);
        assertThat(player.play()).isEqualTo(shape);
    }

    @Test
    void throw_null_pointer_exception_when_creates_a_random_shape_player_with_null(){
        assertThatNullPointerException().isThrownBy(()-> new RandomShapePlayer(null));
    }

    private static List<Arguments> randomPlayerCanGenerateAllValuesArguments(){
        var shapes = Shape.values();
        return IntStream.range(0, shapes.length*3)
                .mapToObj(index-> arguments(index, shapes[index%shapes.length]))
                .collect(toList());
    }
}
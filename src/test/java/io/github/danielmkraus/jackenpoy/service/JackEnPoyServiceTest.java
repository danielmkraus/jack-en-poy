package io.github.danielmkraus.jackenpoy.service;

import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.User;
import io.github.danielmkraus.jackenpoy.domain.player.Player;
import io.github.danielmkraus.jackenpoy.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static io.github.danielmkraus.jackenpoy.domain.MatchResult.LOSE;
import static io.github.danielmkraus.jackenpoy.domain.Shape.PAPER;
import static io.github.danielmkraus.jackenpoy.domain.Shape.ROCK;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JackEnPoyServiceTest {
    @Mock
    Player player;
    @Mock
    MatchRepository repository;
    @InjectMocks
    JackEnPoyService service;

    @Test
    void fail_to_instantiate_with_null_player(){
        assertThatNullPointerException().isThrownBy(()-> new JackEnPoyService(null, player, repository));
    }

    @Test
    void fail_to_instantiate_with_null_opponent(){
        assertThatNullPointerException().isThrownBy(()-> new JackEnPoyService(player, null, repository));
    }

    @Test
    void fail_to_instantiate_with_null_repository(){
        assertThatNullPointerException().isThrownBy(()-> new JackEnPoyService(player, player, null));
    }

    @Test
    void should_play(){
        var sampleUser = getSampleUser();
        var matchList = List.of(Match.builder().build());
        when(player.play()).thenReturn(ROCK,PAPER);
        when(repository.findByUser(eq(sampleUser))).thenReturn(matchList);

        var results = service.play(sampleUser);

        verify(repository).save(argThat(match -> {
            assertThat(match.getUser()).isEqualTo(sampleUser);
            assertThat(match.getShape()).isEqualTo(ROCK);
            assertThat(match.getAgainst()).isEqualTo(PAPER);
            assertThat(match.getResult()).isEqualTo(LOSE);
            assertThat(match.getTimestamp()).isNotNull();
            return true;
        }));
        verify(repository).findByUser(sampleUser);
        assertThat(results).isEqualTo(matchList);
    }

    private User getSampleUser() {
        return User.builder().id("id").build();
    }
}
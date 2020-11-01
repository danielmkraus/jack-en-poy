package org.danielmkraus.jackenpoy.repository;

import org.danielmkraus.jackenpoy.domain.Match;
import org.danielmkraus.jackenpoy.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryMatchRepositoryTest {
    InMemoryMatchRepository repository;

    @BeforeEach
    void setup(){
        repository = new InMemoryMatchRepository();
    }

    @Test
    void should_save(){
        Match match = Match.builder().id("1").build();

        repository.save(match);

        assertThat(repository.findById("1")).isEqualTo(match);
    }

    @Test
    void should_save_without_id(){
        Match match = Match.builder().build();

        repository.save(match);

        assertThat(match.getId()).isNotNull().isNotEmpty();
        assertThat(repository.findById(match.getId())).isEqualTo(match);
    }

    @Test
    void should_not_find_match_without_save_it(){
        assertThat(repository.findById("1")).isNull();
    }

    @Test
    void should_find_by_user(){
        Match firstMatch = Match.builder().user(User.builder().id("1").build()).build();
        Match secondMatch = Match.builder().user(User.builder().id("2").build()).build();
        repository.save(firstMatch);
        repository.save(secondMatch);
        assertThat(repository.findByUser(User.builder().id("1").build()))
                .containsExactly(firstMatch);
        assertThat(repository.findByUser(User.builder().id("2").build()))
                .containsExactly(secondMatch);
    }
}
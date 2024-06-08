package io.github.danielmkraus.jackenpoy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.Shape;
import io.github.danielmkraus.jackenpoy.domain.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryMatchRepositoryTest {

  InMemoryMatchRepository repository;

  @BeforeEach
  void setup() {
    repository = new InMemoryMatchRepository();
  }

  @Test
  void save() {
    Match match = Match.builder().id("1").build();

    repository.save(match);

    assertThat(repository.getScore().getTotal()).isZero();
    assertThat(repository.findById("1")).isEqualTo(match);
  }

  @Test
  void save_and_count_score() {
    Match match = Match.builder().id("1")
        .shape(Shape.ROCK)
        .against(Shape.ROCK)
        .build();

    repository.save(match);

    assertThat(repository.getScore().getTotal()).isEqualTo(1L);
    assertThat(repository.findById("1")).isEqualTo(match);
  }

  @Test
  void save_without_id() {
    Match match = Match.builder().build();

    repository.save(match);

    assertThat(match.getId()).isNotNull().isNotEmpty();
    assertThat(repository.findById(match.getId())).isEqualTo(match);
  }

  @Test
  void not_find_match_without_save_it() {
    assertThat(repository.findById("1")).isNull();
  }

  @Test
  void find_by_user() {
    Match firstMatch = Match.builder().user(User.builder().id("1").build()).build();
    Match secondMatch = Match.builder().user(User.builder().id("2").build()).build();
    repository.save(firstMatch);
    repository.save(secondMatch);
    assertThat(repository.findByUser(User.builder().id("1").build()))
        .containsExactly(firstMatch);
    assertThat(repository.findByUser(User.builder().id("2").build()))
        .containsExactly(secondMatch);
  }

  @Test
  void find_by_user_ordered_by_timestamp_descending() {
    LocalDateTime now = LocalDateTime.now();
    Match firstMatch = Match.builder().user(User.builder().id("1").build())
        .timestamp(now.minusHours(2)).build();
    Match secondMatch = Match.builder().user(User.builder().id("1").build())
        .timestamp(now.minusHours(1)).build();
    Match thirdMatch = Match.builder().user(User.builder().id("1").build()).timestamp(now).build();
    repository.save(firstMatch);
    repository.save(thirdMatch);
    repository.save(secondMatch);
    assertThat(repository.findByUser(User.builder().id("1").build()))
        .containsExactly(thirdMatch, secondMatch, firstMatch);
  }
}
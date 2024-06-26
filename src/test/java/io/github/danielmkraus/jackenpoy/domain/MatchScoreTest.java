package io.github.danielmkraus.jackenpoy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchScoreTest {

  private MatchScore score;

  @BeforeEach
  void setup() {
    score = new MatchScore();
  }

  @Test
  void count_draw() {
    score.process(MatchResult.DRAW);
    assertScore(0L, 1L, 0L);
  }

  @Test
  void count_win() {
    score.process(MatchResult.WIN);
    assertScore(1L, 0L, 0L);
  }

  @Test
  void count_lose() {
    score.process(MatchResult.LOSE);
    assertScore(0L, 0L, 1L);
  }

  @Test
  void count() {
    score.process(MatchResult.DRAW);
    assertScore(0L, 1L, 0L);

    score.process(MatchResult.WIN);
    assertScore(1L, 1L, 0L);

    score.process(MatchResult.LOSE);
    assertScore(1L, 1L, 1L);
  }

  @Test
  void count_with_concurrency() {
    IntStream.range(0, 100).parallel().forEach(i -> {
      score.process(MatchResult.WIN);
      score.process(MatchResult.LOSE);
      score.process(MatchResult.DRAW);
    });
    assertScore(100L, 100L, 100L);
  }

  @Test
  void clone_keeps_old_score_when_cloned_changes() {
    assertScore(0L, 0L, 0L);
    var copied = score.copy();
    score.process(MatchResult.LOSE);

    assertScore(copied, 0L, 0L, 0L);
    assertScore(0L, 0L, 1L);
  }

  private void assertScore(MatchScore score, long firstPlayerWin, long draw, long secondPlayerWin) {
    assertThat(score)
        .extracting(MatchScore::getFirstPlayerWin,
            MatchScore::getDraw,
            MatchScore::getSecondPlayerWin,
            MatchScore::getTotal)
        .isEqualTo(List.of(firstPlayerWin,
            draw,
            secondPlayerWin,
            firstPlayerWin + secondPlayerWin + draw));
  }

  private void assertScore(long firstPlayerWin, long draw, long secondPlayerWin) {
    assertScore(score, firstPlayerWin, draw, secondPlayerWin);
  }
}
package io.github.danielmkraus.jackenpoy.service;

import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.MatchScore;
import io.github.danielmkraus.jackenpoy.domain.User;
import io.github.danielmkraus.jackenpoy.domain.player.Player;
import io.github.danielmkraus.jackenpoy.repository.MatchRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class JackEnPoyService {

  @NonNull
  private final Player player;
  @NonNull
  private final Player opponent;
  @NonNull
  private final MatchRepository matchRepository;

  public List<Match> play(User user) {
    var match = Match.builder()
        .user(user)
        .shape(player.play())
        .against(opponent.play())
        .timestamp(LocalDateTime.now())
        .build();

    matchRepository.save(match);
    log.debug("Match played: {}", match);
    return getMatches(user);
  }

  public List<Match> getMatches(User user) {
    return matchRepository.findByUser(user);
  }

  public MatchScore getScore() {
    return matchRepository.getScore();
  }
}

package io.github.danielmkraus.jackenpoy.repository;

import static java.util.stream.Collectors.toList;

import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.MatchScore;
import io.github.danielmkraus.jackenpoy.domain.User;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMatchRepository implements MatchRepository {

  private final Map<String, Match> matches = new ConcurrentHashMap<>();
  private final MatchScore score = new MatchScore();

  @Override
  public void save(Match match) {
    if (match.getId() == null) {
      match.setId(UUID.randomUUID().toString());
    }
    matches.put(match.getId(), match);
    if (match.getResult() != null) {
      score.process(match.getResult());
    }
  }

  @Override
  public Match findById(String id) {
    return matches.get(id);
  }

  @Override
  public List<Match> findByUser(User user) {
    return matches.values()
        .stream()
        .filter(match -> user.equals(match.getUser()))
        .sorted(Comparator.comparing(Match::getTimestamp).reversed())
        .collect(toList());
  }

  @Override
  public MatchScore getScore() {
    return score.copy();
  }
}

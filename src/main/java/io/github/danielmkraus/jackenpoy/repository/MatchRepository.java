package io.github.danielmkraus.jackenpoy.repository;

import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.MatchScore;
import io.github.danielmkraus.jackenpoy.domain.User;
import java.util.List;

public interface MatchRepository {

  void save(Match match);

  Match findById(String id);

  List<Match> findByUser(User user);

  MatchScore getScore();
}

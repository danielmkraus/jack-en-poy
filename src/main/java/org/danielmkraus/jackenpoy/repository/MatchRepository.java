package org.danielmkraus.jackenpoy.repository;

import org.danielmkraus.jackenpoy.domain.Match;
import org.danielmkraus.jackenpoy.domain.User;

import java.util.List;

public interface MatchRepository {
    void save(Match match);

    Match findById(String id);

    List<Match> findByUser(User user);
}

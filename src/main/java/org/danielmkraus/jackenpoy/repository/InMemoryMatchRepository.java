package org.danielmkraus.jackenpoy.repository;

import org.danielmkraus.jackenpoy.domain.Match;
import org.danielmkraus.jackenpoy.domain.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

public class InMemoryMatchRepository implements MatchRepository {
    private Map<String, Match> matches = new ConcurrentHashMap<>();

    @Override
    public void save(Match match){
        if(match.getId() == null){
            match.setId(UUID.randomUUID().toString());
        }
        matches.put(match.getId(), match);
    }

    @Override
    public Match findById(String id){
        return matches.get(id);
    }

    @Override
    public List<Match> findByUser(User user) {
        return matches.values()
                .stream()
                .filter(match -> user.equals(match.getUser()))
                .collect(toList());
    }
}

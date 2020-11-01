package org.danielmkraus.jackenpoy.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.danielmkraus.jackenpoy.domain.Match;
import org.danielmkraus.jackenpoy.domain.User;
import org.danielmkraus.jackenpoy.domain.player.Player;
import org.danielmkraus.jackenpoy.repository.MatchRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class JackEnPoyService {
    @NonNull
    private final Player player;
    @NonNull
    private final Player opponent;
    @NonNull
    private final MatchRepository matchRepository;

    public List<Match> play(User user){
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
}

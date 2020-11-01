package org.danielmkraus.jackenpoy;

import org.danielmkraus.jackenpoy.controller.JackEnPoyController;
import org.danielmkraus.jackenpoy.domain.Shape;
import org.danielmkraus.jackenpoy.domain.player.FixedShapePlayer;
import org.danielmkraus.jackenpoy.domain.player.RandomShapePlayer;
import org.danielmkraus.jackenpoy.repository.InMemoryMatchRepository;
import org.danielmkraus.jackenpoy.service.JackEnPoyService;

import javax.ws.rs.core.Application;
import java.security.SecureRandom;
import java.util.Set;

public class JackEnPoyApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        return Set.of(createJackEnPoyController());
    }

    public JackEnPoyController createJackEnPoyController() {
        return new JackEnPoyController(createJackEnPoyService());
    }

    private JackEnPoyService createJackEnPoyService() {
        return new JackEnPoyService(
                new FixedShapePlayer(Shape.ROCK),
                new RandomShapePlayer(new SecureRandom()::nextInt),
                new InMemoryMatchRepository());
    }
}

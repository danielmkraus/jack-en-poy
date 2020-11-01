package org.danielmkraus.jackenpoy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.danielmkraus.jackenpoy.controller.JackEnPoyController;
import org.danielmkraus.jackenpoy.domain.Shape;
import org.danielmkraus.jackenpoy.domain.player.FixedShapePlayer;
import org.danielmkraus.jackenpoy.domain.player.RandomShapePlayer;
import org.danielmkraus.jackenpoy.repository.InMemoryMatchRepository;
import org.danielmkraus.jackenpoy.service.JackEnPoyService;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.security.SecureRandom;
import java.util.Set;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class JackEnPoyApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        return Set.of(createJackEnPoyController());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(JacksonContextResolver.class);
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

    @Provider
    public static class JacksonContextResolver implements ContextResolver<ObjectMapper> {
        private static final ObjectMapper om = init();

        private static ObjectMapper init() {
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            om.configure(WRITE_DATES_AS_TIMESTAMPS, false);
            return om;
        }

        @Override
        public ObjectMapper getContext(Class<?> objectType) {
            return om;
        }
    }
}

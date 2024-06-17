package io.github.danielmkraus.jackenpoy;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.danielmkraus.jackenpoy.controller.JackEnPoyController;
import io.github.danielmkraus.jackenpoy.domain.Shape;
import io.github.danielmkraus.jackenpoy.domain.player.FixedShapePlayer;
import io.github.danielmkraus.jackenpoy.domain.player.RandomShapePlayer;
import io.github.danielmkraus.jackenpoy.repository.InMemoryMatchRepository;
import io.github.danielmkraus.jackenpoy.service.JackEnPoyService;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import java.security.SecureRandom;
import java.util.Set;

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
    String a = "a";
    //String b = "b";
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

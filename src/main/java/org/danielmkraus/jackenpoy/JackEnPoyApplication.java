package org.danielmkraus.jackenpoy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.danielmkraus.jackenpoy.controller.JackEnPoyController;
import org.danielmkraus.jackenpoy.domain.Shape;
import org.danielmkraus.jackenpoy.domain.player.FixedShapePlayer;
import org.danielmkraus.jackenpoy.domain.player.RandomShapePlayer;
import org.danielmkraus.jackenpoy.repository.InMemoryMatchRepository;
import org.danielmkraus.jackenpoy.service.JackEnPoyService;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;

public class JackEnPoyApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        return Set.of(createJackEnPoyController());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(LocalDateTimeConverterProvider.class);
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
    public static class LocalDateTimeConverterProvider extends JacksonJsonProvider implements ParamConverterProvider {
        private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

        public LocalDateTimeConverterProvider() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.registerModule(new JavaTimeModule());
            setMapper(mapper);
        }

        @Override
        public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
            if (!rawType.equals(LocalDateTime.class))
                return null;
            return (ParamConverter<T>) converter;
        }

        public static class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {
            @Override
            public LocalDateTime fromString(String value) {
                return LocalDateTime.parse(value);
            }

            @Override
            public String toString(LocalDateTime value) {
                return value.toString();
            }
        }
    }
}

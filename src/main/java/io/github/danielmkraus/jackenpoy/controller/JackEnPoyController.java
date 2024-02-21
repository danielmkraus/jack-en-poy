package io.github.danielmkraus.jackenpoy.controller;

import jakarta.ws.rs.*;
import lombok.AllArgsConstructor;
import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.MatchScore;
import io.github.danielmkraus.jackenpoy.domain.User;
import io.github.danielmkraus.jackenpoy.service.JackEnPoyService;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@AllArgsConstructor
@Path("/v1/jack-en-poy")
public class JackEnPoyController {
    private final JackEnPoyService service;

    @POST
    @Path("/{userId}")
    @Produces(APPLICATION_JSON)
    public List<Match> play(@PathParam("userId") String userId) {
        return service.play(user(userId));
    }

    @GET
    @Path("/{userId}")
    @Produces(APPLICATION_JSON)
    public List<Match> getMatches(@PathParam("userId") String userId) {
        return service.getMatches(user(userId));
    }

    @GET
    @Produces(APPLICATION_JSON)
    public MatchScore getScore() {
        return service.getScore();
    }

    private User user(String userId) {
        return User.builder().id(userId).build();
    }
}

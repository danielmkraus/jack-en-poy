package org.danielmkraus.jackenpoy.controller;

import lombok.AllArgsConstructor;
import org.danielmkraus.jackenpoy.domain.Match;
import org.danielmkraus.jackenpoy.domain.User;
import org.danielmkraus.jackenpoy.service.JackEnPoyService;

import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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

    private User user(String userId) {
        return User.builder().id(userId).build();
    }
}

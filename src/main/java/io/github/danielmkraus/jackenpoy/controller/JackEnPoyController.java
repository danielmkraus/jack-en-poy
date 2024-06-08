package io.github.danielmkraus.jackenpoy.controller;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.MatchScore;
import io.github.danielmkraus.jackenpoy.domain.User;
import io.github.danielmkraus.jackenpoy.service.JackEnPoyService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;
import lombok.AllArgsConstructor;

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

package io.github.danielmkraus.jackenpoy.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Match {

  private final User user;
  private final Shape shape;
  private final Shape against;
  private final LocalDateTime timestamp;
  private String id;

  public MatchResult getResult() {
    if (shape != null && against != null) {
      return shape.against(against);
    }
    return null;
  }
}

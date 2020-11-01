package org.danielmkraus.jackenpoy.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Match {
    private String id;
    private final User user;
    private final Shape shape;
    private final Shape against;

    public MatchResult getResult(){
        return shape.against(against);
    }
}

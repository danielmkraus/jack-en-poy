package org.danielmkraus.jackenpoy.domain;

import java.util.Objects;

public enum Shape {
    PAPER {
        @Override
        MatchResult againstOther(Shape other) {
            return other == ROCK ? MatchResult.WIN : MatchResult.LOOSE ;
        }
    },
    ROCK {
        @Override
        MatchResult againstOther(Shape other) {
            return other == SCISSOR ? MatchResult.WIN : MatchResult.LOOSE ;
        }
    },
    SCISSOR {
        @Override
        MatchResult againstOther(Shape other) {
            return other == PAPER ? MatchResult.WIN : MatchResult.LOOSE ;
        }
    };

    MatchResult against(Shape shape){
        Objects.requireNonNull(shape);
        if(shape == this){
            return MatchResult.DRAW;
        }
        return againstOther(shape);
    }

    abstract MatchResult againstOther(Shape other);
}

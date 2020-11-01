package org.danielmkraus.jackenpoy.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class MatchScore implements Cloneable {
    private Long draw = 0L;
    private Long firstPlayerWin = 0L;
    private Long secondPlayerWin = 0L;

    public void process(MatchResult result) {
        synchronized (this) {
            switch (result) {
                case DRAW:
                    draw++;
                    break;
                case WIN:
                    firstPlayerWin++;
                    break;
                case LOSE:
                    secondPlayerWin++;
                    break;
                default:
                    throw new IllegalStateException("Unrecognized result: " + result);
            }
        }
    }

    public long getTotal() {
        synchronized (this) {
            return draw + firstPlayerWin + secondPlayerWin;
        }
    }

    @SneakyThrows
    public MatchScore clone() {
        return (MatchScore) super.clone();
    }
}

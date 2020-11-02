package org.danielmkraus.jackenpoy.domain;

import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchScore {
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

    public MatchScore copy() {
        synchronized (this) {
            return MatchScore.builder()
                    .draw(draw)
                    .firstPlayerWin(firstPlayerWin)
                    .secondPlayerWin(secondPlayerWin)
                    .build();
        }
    }
}

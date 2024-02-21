package io.github.danielmkraus.jackenpoy;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.cli.ParseException;
import io.github.danielmkraus.jackenpoy.domain.Match;
import io.github.danielmkraus.jackenpoy.domain.MatchScore;
import io.github.danielmkraus.jackenpoy.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class JackEnPoyIntegrationTest {
    public static final String FIRST_PLAYER_USER_ID = "first";
    public static final String SECOND_PLAYER_USER_ID = "second";
    public static final String THIRD_PLAYER_USER_ID = "third";
    private final Gson gson = Converters.registerAll(new GsonBuilder()).create();
    private Server server;
    private JackEnPoyClient client;

    @BeforeEach
    void setup() throws ParseException, IOException {
        ServerSocket socket = new ServerSocket(0);
        String port = Integer.toString(socket.getLocalPort());
        socket.close();
        server = Server.start(new String[]{"-p", port});
        client = new JackEnPoyClient("http://127.0.0.1:" + port);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void play() throws ExecutionException, InterruptedException {
        HttpResponse<String> response = client.play(FIRST_PLAYER_USER_ID).get();
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(gson.fromJson(response.body(), Match[].class)).hasSize(1)
                .extracting(Match::getUser)
                .containsExactly(
                        user(FIRST_PLAYER_USER_ID));
    }

    @Test
    void score() throws ExecutionException, InterruptedException {
        HttpResponse<String> response = client.score().get();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(gson.fromJson(response.body(), MatchScore.class).getTotal()).isZero();
    }

    @Test
    void play_concurrently() {
        IntStream.range(0, 100).parallel().forEach(i -> {
            client.play(FIRST_PLAYER_USER_ID);
            client.play(SECOND_PLAYER_USER_ID);
            client.play(THIRD_PLAYER_USER_ID);
        });

        await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
            HttpResponse<String> firstUserMatchesResponse = client.getMatches(FIRST_PLAYER_USER_ID).get();
            HttpResponse<String> secondUserMatchesResponse = client.getMatches(SECOND_PLAYER_USER_ID).get();
            HttpResponse<String> thirdUserMatchesResponse = client.getMatches(THIRD_PLAYER_USER_ID).get();

            HttpResponse<String> response = client.score().get();
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(gson.fromJson(response.body(), MatchScore.class).getTotal()).isEqualTo(300L);
            assertThat(firstUserMatchesResponse.statusCode()).isEqualTo(200);
            assertThat(secondUserMatchesResponse.statusCode()).isEqualTo(200);
            assertThat(thirdUserMatchesResponse.statusCode()).isEqualTo(200);
            assertThat(gson.fromJson(firstUserMatchesResponse.body(), Match[].class)).hasSize(100)
                    .extracting(Match::getUser)
                    .containsOnly(
                            user(FIRST_PLAYER_USER_ID));
            assertThat(gson.fromJson(secondUserMatchesResponse.body(), Match[].class)).hasSize(100)
                    .extracting(Match::getUser)
                    .containsOnly(
                            user(SECOND_PLAYER_USER_ID));
            assertThat(gson.fromJson(thirdUserMatchesResponse.body(), Match[].class)).hasSize(100)
                    .extracting(Match::getUser)
                    .containsOnly(
                            user(THIRD_PLAYER_USER_ID));
        });
    }

    private User user(String id) {
        return User.builder()
                .id(id)
                .build();
    }
}

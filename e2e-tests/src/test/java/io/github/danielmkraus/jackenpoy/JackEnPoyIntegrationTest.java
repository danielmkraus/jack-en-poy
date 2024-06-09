package io.github.danielmkraus.jackenpoy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JackEnPoyIntegrationTest {

  public static final String FIRST_PLAYER_USER_ID = "first";
  public static final String SECOND_PLAYER_USER_ID = "second";
  public static final String THIRD_PLAYER_USER_ID = "third";
  private JackEnPoyClient client;

  @BeforeEach
  void setup() {
    client = new JackEnPoyClient(System.getProperty("JACK_EN_POY_URL"));
  }

  @Test
  void play() throws ExecutionException, InterruptedException {
    HttpResponse<String> response = client.play(FIRST_PLAYER_USER_ID).get();
    assertThat(response.statusCode()).isEqualTo(200);
  }

  @Test
  void score() throws ExecutionException, InterruptedException {
    HttpResponse<String> response = client.score().get();
    assertThat(response.statusCode()).isEqualTo(200);
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
      HttpResponse<String> secondUserMatchesResponse = client.getMatches(SECOND_PLAYER_USER_ID)
          .get();
      HttpResponse<String> thirdUserMatchesResponse = client.getMatches(THIRD_PLAYER_USER_ID).get();

      HttpResponse<String> response = client.score().get();
      assertThat(response.statusCode()).isEqualTo(200);
      assertThat(firstUserMatchesResponse.statusCode()).isEqualTo(200);
      assertThat(secondUserMatchesResponse.statusCode()).isEqualTo(200);
      assertThat(thirdUserMatchesResponse.statusCode()).isEqualTo(200);
    });
  }

}

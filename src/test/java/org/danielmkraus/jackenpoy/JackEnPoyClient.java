package org.danielmkraus.jackenpoy;

import lombok.AllArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class JackEnPoyClient {
    private static final String JACK_EN_POY_ENDPOINT = "/api/v1/jack-en-poy";
    private final String serverUrl;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public CompletableFuture<HttpResponse<String>> play(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + JACK_EN_POY_ENDPOINT + "/" + userId))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public CompletableFuture<HttpResponse<String>> getMatches(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + JACK_EN_POY_ENDPOINT + "/" + userId))
                .GET().build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public CompletableFuture<HttpResponse<String>> score() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + JACK_EN_POY_ENDPOINT))
                .GET().build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}

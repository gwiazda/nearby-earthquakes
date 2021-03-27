package com.gwiazda.earthquake.infrastructure;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class UsgsEarthquakeRestClient {

    private final EnvironmentProperties environmentProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final HttpRequest httpRequest;

    UsgsEarthquakeRestClient(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
        this.objectMapper = initializeObjectMapper();
        this.httpClient = initializeHttpClient();
        this.httpRequest = initializeHttpRequest();
    }

    public UsgsEarthquakeRestDto getEarthquakesFromLast30Days() throws IOException, InterruptedException {
        HttpResponse<String> earthQuakesResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(earthQuakesResponse.body(), UsgsEarthquakeRestDto.class);
    }

    private ObjectMapper initializeObjectMapper() {
        return new ObjectMapper()
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    private HttpClient initializeHttpClient() {
        return HttpClient.newBuilder().build();
    }

    private HttpRequest initializeHttpRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(environmentProperties.getEarthquakeServiceUrl()))
                .GET()
                .build();
    }
}

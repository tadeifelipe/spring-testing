package com.tadeifelipe.springtesting.weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WeatherClientTest {

    private WeatherClient weatherClient;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        weatherClient = new WeatherClient(restTemplate, "http://localhost:8089", "someApiKey");
    }

    @Test
    public void shouldCallWeatherService() {
        WeatherResponse response = new WeatherResponse("Sunny", "Clear sky");
        when(restTemplate.getForObject("http://localhost:8089/data/2.5/weather?q=Osvaldo Cruz,br&appid=someApiKey", WeatherResponse.class))
                .thenReturn(response);

        var actualResponse = weatherClient.fetchWeather();

        assertThat(actualResponse, is(Optional.of(response)));
    }

    @Test
    public void shouldReturnEmptyOptionalIfWeatherServiceIsUnavailable() throws Exception {
        when(restTemplate.getForObject("http://localhost:8089/data/2.5/weather?q=Osvaldo Cruz,br&appid=someApiKey", WeatherResponse.class))
                .thenThrow(new RestClientException("something went wrong"));

        var actualResponse = weatherClient.fetchWeather();

        assertThat(actualResponse, is(Optional.empty()));
    }
}
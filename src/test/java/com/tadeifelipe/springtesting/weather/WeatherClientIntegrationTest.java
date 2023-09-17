package com.tadeifelipe.springtesting.weather;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.tadeifelipe.springtesting.helper.FileLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.common.ContentTypes.CONTENT_TYPE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@WireMockTest(httpPort = 8089)
public class WeatherClientIntegrationTest {

    @Autowired
    private WeatherClient weatherClient;

    @Test
    public void shouldCallWeatherService() throws Exception {
        stubFor(get(urlEqualTo("/data/2.5/weather?q=Osvaldo%20Cruz,br&appid=someApiKey"))
                .willReturn(aResponse()
                        .withBody(FileLoader.read("classpath:weatherApiResponse.json"))
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));

        var weatherResponse = weatherClient.fetchWeather();

        var expectedResponse = Optional.of(new WeatherResponse("sunny", "Clear sky"));
        assertThat(weatherResponse, is(expectedResponse));
    }
}

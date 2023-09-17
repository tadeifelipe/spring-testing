package com.tadeifelipe.springtesting.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadeifelipe.springtesting.helper.FileLoader;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class WeatherResponseTest {

    @Test
    public void shouldDeserializeJson() throws Exception {
        var jsonResponse = FileLoader.read("classpath:weatherApiResponse.json");
        var expectedResponse = new WeatherResponse("sunny", "Clear sky");

        var parsedResponse = new ObjectMapper().readValue(jsonResponse, WeatherResponse.class);

        assertThat(parsedResponse, is(expectedResponse));
    }
}
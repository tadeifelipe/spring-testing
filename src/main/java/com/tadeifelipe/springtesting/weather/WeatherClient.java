package com.tadeifelipe.springtesting.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static java.lang.String.format;

@Component
public class WeatherClient {

    private final RestTemplate restTemplate;
    private final String weatherUrl;
    private final String weatherApiKey;

    public WeatherClient (RestTemplate restTemplate,
                          @Value("${weather.url}") String weatherUrl,
                          @Value("${weather.api_key}") String weatherApiKey) {
        this.restTemplate = restTemplate;
        this.weatherUrl = weatherUrl;
        this.weatherApiKey = weatherApiKey;
    }

    public Optional<WeatherResponse> fetchWeather() {
        String CITY = "Osvaldo Cruz,br";
        var url = format("%s/data/2.5/weather?q=%s&appid=%s", weatherUrl, CITY, weatherApiKey);

        try {
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
            return Optional.ofNullable(response);
        } catch (RestClientException ex) {
            return Optional.empty();
        }
    }
}

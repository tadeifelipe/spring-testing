package com.tadeifelipe.springtesting.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    public List<Weather> weather;

    public WeatherResponse() {}

    public WeatherResponse(String main, String description) {
        this.weather = Collections.singletonList(new Weather(main, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherResponse that = (WeatherResponse) o;
        return Objects.equals(weather, that.weather);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weather);
    }

    public String getSummary() {
        return weather.stream()
                .map(w -> w.main + ": " + w.description)
                .collect(Collectors.joining("\n"));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {

        private String main;
        private String description;

        public Weather() {}

        public Weather(String main, String description) {
            this.main = main;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Weather weather = (Weather) o;
            return Objects.equals(main, weather.main) && Objects.equals(description, weather.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(main, description);
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

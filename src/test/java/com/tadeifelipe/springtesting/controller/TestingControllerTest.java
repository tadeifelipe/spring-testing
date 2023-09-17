package com.tadeifelipe.springtesting.controller;

import com.tadeifelipe.springtesting.model.Person;
import com.tadeifelipe.springtesting.model.PersonRepository;
import com.tadeifelipe.springtesting.weather.WeatherClient;
import com.tadeifelipe.springtesting.weather.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class TestingControllerTest {

    private TestingController controller;

    @Mock
    private PersonRepository repository;

    @Mock
    private WeatherClient weatherClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new TestingController(repository, weatherClient);
    }

    @Test
    public void shouldReturnHelloWorld() {
        assertThat(controller.hello(), is("Hello World!"));
    }

    @Test
    public void shouldReturnFullNameOfAPerson() {
        Person peter = new Person("Peter", "Pan");
        when(repository.findByLastName("Pan")).thenReturn(Optional.of(peter));

        var response = controller.hello("Pan");
        assertThat(response, is("Hello Peter Pan!"));
    }

    @Test
    public void shouldReturnIfAPersonIsUnknown() {
        when(repository.findByLastName(anyString())).thenReturn(Optional.empty());

        var response = controller.hello("Pan");

        assertThat(response, is("Who is Pan you are talking about?"));
    }

    @Test
    public void shouldSaveAPerson() {
        Person person = new Person("Peter", "Pan");
        when(repository.save(any())).thenReturn(person);

        var response = controller.person(person);

        assertThat(response, is(person));
    }

    @Test
    public void shouldReturnAWeatherResponse() {
        WeatherResponse weatherResponse = new WeatherResponse("sunny", "Clear sky");
        when(weatherClient.fetchWeather()).thenReturn(Optional.of(weatherResponse));

        var response = controller.weather();

        assertThat(response, is("sunny: Clear sky"));
    }

    @Test
    public void shouldReturnErrorMessageIfWeatherClientIsUnavailable() {
        when(weatherClient.fetchWeather()).thenReturn(Optional.empty());

        var response = controller.weather();

        assertThat(response, is("Sorry, I couldn't fetch the weather for you :("));
    }
}
package com.tadeifelipe.springtesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadeifelipe.springtesting.model.Person;
import com.tadeifelipe.springtesting.model.PersonRepository;
import com.tadeifelipe.springtesting.weather.WeatherClient;
import com.tadeifelipe.springtesting.weather.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestingController.class)
public class TestingControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private WeatherClient weatherClient;

    @Test
    public void shouldReturnHelloWorld() throws Exception {
        mockMvc
                .perform(get("/hello"))
                .andExpect(content().string("Hello World!"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldReturnFullName() throws Exception {
        Person person = new Person("Peter", "Pan");
        when(personRepository.findByLastName(anyString())).thenReturn(Optional.of(person));

        mockMvc
                .perform(get("/hello/Pan"))
                .andExpect(content().string("Hello Peter Pan!"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldSaveThePerson() throws Exception {
        Person person = new Person("Peter", "Pan");
        when(personRepository.save(any())).thenReturn(person);

        mockMvc
                .perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(person)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Peter"))
                .andExpect(jsonPath("$.lastName").value("Pan"));
    }

    @Test
    public void shouldReturnCurrentWeather() throws Exception {
        var weatherResponse = new WeatherResponse("Sunny", "Clear sky");
        when(weatherClient.fetchWeather()).thenReturn(Optional.of(weatherResponse));

        mockMvc.perform(get("/weather"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Sunny: Clear sky"));
    }
}

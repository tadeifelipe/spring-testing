package com.tadeifelipe.springtesting.controller;

import com.tadeifelipe.springtesting.model.Person;
import com.tadeifelipe.springtesting.model.PersonRepository;
import com.tadeifelipe.springtesting.weather.WeatherClient;
import com.tadeifelipe.springtesting.weather.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

@RestController
public class TestingController {

    private final PersonRepository repository;
    private final WeatherClient weatherClient;

    @Autowired
    public TestingController(PersonRepository repository, WeatherClient weatherClient) {
        this.repository = repository;
        this.weatherClient = weatherClient;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/hello/{lastName}")
    public String hello(@PathVariable String lastName){
        var foundPerson = repository.findByLastName(lastName);

        return foundPerson
                .map(person -> format("Hello %s %s!", person.getName(), person.getLastName()))
                .orElse(format("Who is %s you are talking about?", lastName));
    }

    @PostMapping("/person")
    public Person person(@RequestBody Person person) {
       return repository.save(person);
    }

    @GetMapping("/weather")
    public String weather() {
        return weatherClient.fetchWeather()
                .map(WeatherResponse::getSummary)
                .orElse("Sorry, I couldn't fetch the weather for you :(");
    }
}

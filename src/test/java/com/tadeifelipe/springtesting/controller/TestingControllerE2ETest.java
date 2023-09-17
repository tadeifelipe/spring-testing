package com.tadeifelipe.springtesting.controller;


import com.tadeifelipe.springtesting.model.Person;
import com.tadeifelipe.springtesting.model.PersonRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingControllerE2ETest {

    @Autowired
    private PersonRepository personRepository;

    @LocalServerPort
    private int port;

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldReturnHelloWorld() {
        when()
                .get(format("http://localhost:%s/hello", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello World!"));
    }

    @Test
    public void shouldReturnGreeting() {
        var peter = new Person("Peter", "Pan");
        personRepository.save(peter);

        when()
                .get(format("http://localhost:%s/hello/Pan", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello Peter Pan!"));
    }

    @Test
    public void shouldReturnSavedPerson() {
        var peter = new Person("Peter", "Pan");

        given()
                .contentType(ContentType.JSON)
                .body(peter)
                .when()
                .post(format("http://localhost:%s/person", port))
                .then()
                .statusCode(is(200))
                .body(
                        "name", is(peter.getName()),
                        "lastName", is(peter.getLastName())
                );
    }
}

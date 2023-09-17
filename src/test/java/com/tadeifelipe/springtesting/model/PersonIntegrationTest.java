package com.tadeifelipe.springtesting.model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class PersonIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldSavePerson() {
        Person person = new Person("Peter", "Pan");

        personRepository.save(person);

        Optional<Person> actualResponse = personRepository.findByLastName("Pan");

        assertThat(actualResponse, is(Optional.of(person)));
    }
}
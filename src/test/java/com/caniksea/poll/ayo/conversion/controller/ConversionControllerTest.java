package com.caniksea.poll.ayo.conversion.controller;

import com.caniksea.poll.ayo.conversion.exception.UnsupportedConversionTypeException;
import com.caniksea.poll.ayo.conversion.factory.RequestFactory;
import com.caniksea.poll.ayo.conversion.model.Request;
import com.caniksea.poll.ayo.conversion.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConversionControllerTest {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private RequestFactory requestFactory;
    @LocalServerPort private int port;
    private String url;

    @BeforeEach void setUp() {
        this.url = "http://localhost:" + this.port + "/api/convert";
    }

    @Test void convert_withoutToUnit() {
        Request request = this.requestFactory.build("temperature", "celsius", "", 52, 8);
        ResponseEntity responseEntity = this.restTemplate.postForEntity(this.url, request, Response.class);
        assertAll(
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertTrue(responseEntity.getStatusCode().is2xxSuccessful())
        );
    }

    @Test void convert_withToUnit() {
        Request request = this.requestFactory.build("linear", "yard", "kilometer", 158, 8);
        ResponseEntity responseEntity = this.restTemplate.postForEntity(this.url, request, Response.class);
        assertAll(
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertTrue(responseEntity.getStatusCode().is2xxSuccessful())
        );
    }

    @Test void convert_expected4XX() {
        Request request = this.requestFactory.build("temperature", "celsiuss", "", 52, 8);
        ResponseEntity responseEntity = this.restTemplate.postForEntity(this.url, request, Response.class);
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }
}
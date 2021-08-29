package com.caniksea.poll.ayo.conversion.service;

import com.caniksea.poll.ayo.conversion.exception.InvalidOrUnsupportedUnitException;
import com.caniksea.poll.ayo.conversion.exception.UnsupportedConversionTypeException;
import com.caniksea.poll.ayo.conversion.factory.RequestFactory;
import com.caniksea.poll.ayo.conversion.model.Request;
import com.caniksea.poll.ayo.conversion.model.Response;
import com.caniksea.poll.ayo.conversion.processor.IProcessor;
import com.caniksea.poll.ayo.conversion.processor.impl.LinearProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConversionServiceTest {

    @Autowired private RequestFactory requestFactory;
    @Autowired private ConversionService service;

    @Test void getProcessor() throws UnsupportedConversionTypeException {
        IProcessor processor = this.service.getProcessor("linear");
        assertAll(
                () -> assertNotNull(processor),
                () -> assertSame(LinearProcessor.class, processor.getClass())
        );
    }

    @Test void convert() throws UnsupportedConversionTypeException, InvalidOrUnsupportedUnitException {
        IProcessor processor = this.service.getProcessor("temperature");
        Request request = this.requestFactory.build("linear", "celsius", "", 10, -1);
        Response response = this.service.convert(processor, request);
        assertNotNull(response);
        System.out.println(response);
    }
}
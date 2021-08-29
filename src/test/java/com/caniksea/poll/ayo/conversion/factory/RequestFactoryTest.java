package com.caniksea.poll.ayo.conversion.factory;

import com.caniksea.poll.ayo.conversion.config.ConversionConfig;
import com.caniksea.poll.ayo.conversion.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(ConversionConfig.class)
class RequestFactoryTest {
    @Autowired private ConversionConfig conversionConfig;
    private RequestFactory factory;

    @BeforeEach void setUp() {
        assertNotNull(this.conversionConfig);
        this.factory = new RequestFactory(this.conversionConfig);
    }

    @Test void build() {
        Request request = this.factory.build("temperature", "kelvin", "", 3, 3);
        assertNotNull(request);
    }

    @Test void checkDecimalPlace() {
        Request entity = this.factory.build("temperature", "kelvin", "", 3, -10);
        Request request = this.factory.checkDecimalPlace(entity);
        assertAll(
                () -> assertNotNull(request),
                () -> assertEquals(8, request.getDecimalPlace())
        );
    }
}
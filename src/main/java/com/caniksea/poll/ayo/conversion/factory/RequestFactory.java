package com.caniksea.poll.ayo.conversion.factory;

import com.caniksea.poll.ayo.conversion.config.ConversionConfig;
import com.caniksea.poll.ayo.conversion.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for request pojo
 */
@Component
public class RequestFactory {
    private final ConversionConfig config;

    @Autowired public RequestFactory(ConversionConfig config) {
        this.config = config;
    }

    public Request build(String type, String fromUnit, String toUnit, double data, int decimalPlace) {
        return Request.builder().type(type).fromUnit(fromUnit)
                .toUnit(toUnit).data(data).decimalPlace(decimalPlace).build();
    }

    /**
     * check decimal place. Use default decimal place if invalid decimal place is provided.
     * @param request
     * @return
     */
    public Request checkDecimalPlace(Request request) {
        int decimalPlace = request.getDecimalPlace() <= 0 ? this.config.getDefaultDP() : request.getDecimalPlace();
        return request.toBuilder().decimalPlace(decimalPlace).build();
    }
}

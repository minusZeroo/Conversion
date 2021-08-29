package com.caniksea.poll.ayo.conversion.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component @Slf4j
public class ConversionConfig {

    @Value("${conversion.default.decimal.places}")
    private String defaultDP;

    public int getDefaultDP() {
        try {
            return Integer.parseInt(defaultDP);
        } catch (NumberFormatException e) {
            int defaultDp = 8;
            log.warn("Invalid config property: {}, value: {}. Using default as {}",
                    "conversion.default.decimal.places", defaultDP, defaultDp);
            return defaultDp;
        }
    }
}

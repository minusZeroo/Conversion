package com.caniksea.poll.ayo.conversion.processor.impl;

import com.caniksea.poll.ayo.conversion.exception.InvalidOrUnsupportedUnitException;
import com.caniksea.poll.ayo.conversion.processor.IProcessor;
import com.caniksea.poll.ayo.conversion.model.GenericPayload;
import com.caniksea.poll.ayo.conversion.model.Request;
import com.caniksea.poll.ayo.conversion.model.Response;
import com.caniksea.poll.ayo.conversion.model.TemperaturePayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Processor for temperature conversion
 */
@Slf4j
public class TemperatureProcessor implements IProcessor {

    /**
     * convert to supported temperature units.
     * @param converter
     * @param data
     * @param decimalPlace
     * @return
     */
    private TemperaturePayload getTemperaturePayload(CONVERTER converter, double data, int decimalPlace) {
        double celsius = converter.toCelsius(data, decimalPlace);
        double fahrenheit = converter.toFahrenheit(data, decimalPlace);
        double kelvin = converter.toKelvin(data, decimalPlace);
        return new TemperaturePayload(celsius, fahrenheit, kelvin);
    }

    @Override
    public Response convert(Request request) throws InvalidOrUnsupportedUnitException {
        String fromUnit = request.getFromUnit();
        CONVERTER fromConverter = CONVERTER.lookUp(fromUnit);
        double data = request.getData();
        int decimalPlace = request.getDecimalPlace();
        String toUnit = request.getToUnit();
        if (!StringUtils.isEmpty(toUnit)) {
            try {
                CONVERTER toConverter = CONVERTER.lookUp(toUnit);
                double answer = toConverter.toTempUnit(data, decimalPlace, fromConverter);
                return new Response(request, new GenericPayload(answer));
            } catch (InvalidOrUnsupportedUnitException e) {
                log.warn("Invalid temperature unit: {}. Converting to all.", toUnit);
                TemperaturePayload temperaturePayload = getTemperaturePayload(fromConverter, data, decimalPlace);
                return new Response(request, temperaturePayload);
            }
        } else {
            TemperaturePayload temperaturePayload = getTemperaturePayload(fromConverter, data, decimalPlace);
            return new Response(request, temperaturePayload);
        }
    }

    enum CONVERTER {
        CELSIUS {
            @Override public double toCelsius(double temp, int decimalPlace) {
                return BigDecimal.valueOf(temp).setScale(decimalPlace).doubleValue();
            }

            @Override public double toFahrenheit(double temp, int decimalPlace) {
                BigDecimal bigDecimal = BigDecimal.valueOf((temp * 9/5) + 32)
                        .setScale(decimalPlace, RoundingMode.HALF_UP);
                return bigDecimal.doubleValue();
            }

            @Override public double toKelvin(double temp, int decimalPlace) {
                BigDecimal value = BigDecimal.valueOf(temp + 273.15)
                        .setScale(decimalPlace, RoundingMode.HALF_UP);
                return value.doubleValue();
            }

            @Override
            public double toTempUnit(double unit, int decimalPlace, CONVERTER converter) {
                return converter.toCelsius(unit, decimalPlace);
            }
        },
        FAHRENHEIT {
            @Override public double toCelsius(double temp, int decimalPlace) {
                BigDecimal value = BigDecimal.valueOf((temp - 32) * 5/9)
                        .setScale(decimalPlace, RoundingMode.HALF_UP);
                return value.doubleValue();
            }

            @Override public double toFahrenheit(double temp, int decimalPlace) {
                return BigDecimal.valueOf(temp).setScale(decimalPlace).doubleValue();
            }

            @Override public double toKelvin(double temp, int decimalPlace) {
                BigDecimal value = BigDecimal.valueOf((temp - 32) * 5/9 + 273.15)
                        .setScale(decimalPlace, RoundingMode.HALF_UP);
                return value.doubleValue();
            }

            @Override
            public double toTempUnit(double unit, int decimalPlace, CONVERTER converter) {
                return converter.toFahrenheit(unit, decimalPlace);
            }
        },
        KELVIN {
            @Override
            public double toCelsius(double temp, int decimalPlace) {
                BigDecimal value = BigDecimal.valueOf(temp - 273.15)
                        .setScale(decimalPlace, RoundingMode.HALF_UP);
                return value.doubleValue();
            }

            @Override
            public double toFahrenheit(double temp, int decimalPlace) {
                BigDecimal value = BigDecimal.valueOf((temp - 273.15) * 9/5 + 32)
                        .setScale(decimalPlace, RoundingMode.HALF_UP);
                return value.doubleValue();
            }

            @Override
            public double toKelvin(double temp, int decimalPlace) {
                return BigDecimal.valueOf(temp).setScale(decimalPlace).doubleValue();
            }

            @Override
            public double toTempUnit(double unit, int decimalPlace, CONVERTER converter) {
                return converter.toKelvin(unit, decimalPlace);
            }
        };

        public abstract double toCelsius(double temp, int decimalPlace);
        public abstract double toFahrenheit(double temp, int decimalPlace);
        public abstract double toKelvin(double temp, int decimalPlace);
        public abstract double toTempUnit(double unit, int decimalPlace, CONVERTER converter);

        public static CONVERTER lookUp(String tempStr) throws InvalidOrUnsupportedUnitException {
            try {
                return CONVERTER.valueOf(tempStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                String error = String.format("Invalid temperature unit: %s", tempStr);
                throw new InvalidOrUnsupportedUnitException(error, e);
            }
        }
    }
}

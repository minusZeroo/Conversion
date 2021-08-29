package com.caniksea.poll.ayo.conversion.processor.impl;

import com.caniksea.poll.ayo.conversion.exception.InvalidOrUnsupportedUnitException;
import com.caniksea.poll.ayo.conversion.processor.IProcessor;
import com.caniksea.poll.ayo.conversion.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Processor for linear conversion.
 */
@Slf4j
public class LinearProcessor implements IProcessor {

    /**
     * convert to supported linear units
     * @param converter
     * @param data
     * @param decimalPlace
     * @return
     */
    private LinearPayload getLinearPayload(CONVERTER converter, double data, int decimalPlace) {
        double inch = converter.toInch(data, decimalPlace);
        double foot = converter.toFoot(data, decimalPlace);
        double yard = converter.toYard(data, decimalPlace);
        double kilometer = converter.toKilometres(data, decimalPlace);
        double nauticalMile = converter.toNauticalMiles(data, decimalPlace);
        return new LinearPayload(inch, foot, yard, kilometer, nauticalMile);
    }

    enum CONVERTER {
        INCH {
            @Override
            public double toInch(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).setScale(decimalPlace).doubleValue();
            }

            @Override
            public double toFoot(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .divide(BigDecimal.valueOf(12), decimalPlace, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            @Override
            public double toYard(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .divide(BigDecimal.valueOf(36), decimalPlace, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            @Override
            public double toKilometres(double linear, int decimalPlace) {
                BigDecimal inch = BigDecimal.valueOf(0.0000254);
                return BigDecimal.valueOf(linear)
                        .multiply(inch).setScale(decimalPlace, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            @Override
            public double toNauticalMiles(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(127).divide(BigDecimal.valueOf(9260000), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toLinearUnit(double linear, int decimalPlace, CONVERTER converter) {
                return converter.toInch(linear, decimalPlace);
            }
        },
        FOOT {
            @Override
            public double toInch(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).multiply(BigDecimal.valueOf(12))
                        .setScale(decimalPlace, RoundingMode.HALF_UP).doubleValue();
            }

            @Override
            public double toFoot(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).setScale(decimalPlace).doubleValue();
            }

            @Override
            public double toYard(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .divide(BigDecimal.valueOf(3), decimalPlace, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            @Override
            public double toKilometres(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(0.0003048)).setScale(decimalPlace, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            @Override
            public double toNauticalMiles(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(381).divide(BigDecimal.valueOf(2315000), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toLinearUnit(double linear, int decimalPlace, CONVERTER converter) {
                return converter.toFoot(linear, decimalPlace);
            }
        },
        YARD {
            @Override
            public double toInch(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).multiply(BigDecimal.valueOf(36))
                        .setScale(decimalPlace, RoundingMode.HALF_UP).doubleValue();
            }

            @Override
            public double toFoot(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).multiply(BigDecimal.valueOf(3))
                        .setScale(decimalPlace, RoundingMode.HALF_UP).doubleValue();
            }

            @Override
            public double toYard(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).setScale(decimalPlace).doubleValue();
            }

            @Override
            public double toKilometres(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(0.0009144)).setScale(decimalPlace, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            @Override
            public double toNauticalMiles(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(1143).divide(BigDecimal.valueOf(2315000), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toLinearUnit(double linear, int decimalPlace, CONVERTER converter) {
                return converter.toYard(linear, decimalPlace);
            }
        },
        NAUTICALMILE {
            @Override
            public double toInch(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(9260000).divide(BigDecimal.valueOf(127), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toFoot(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(2315000).divide(BigDecimal.valueOf(381), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toYard(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(2315000).divide(BigDecimal.valueOf(1143), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toKilometres(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(1.852)).setScale(decimalPlace, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            @Override
            public double toNauticalMiles(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).setScale(decimalPlace).doubleValue();
            }

            @Override
            public double toLinearUnit(double linear, int decimalPlace, CONVERTER converter) {
                return converter.toNauticalMiles(linear, decimalPlace);
            }
        },
        KILOMETER {
            @Override
            public double toInch(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(5000000).divide(BigDecimal.valueOf(127), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toFoot(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(1250000).divide(BigDecimal.valueOf(381), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toYard(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(1250000).divide(BigDecimal.valueOf(1143), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toKilometres(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear).setScale(decimalPlace).doubleValue();
            }

            @Override
            public double toNauticalMiles(double linear, int decimalPlace) {
                return BigDecimal.valueOf(linear)
                        .multiply(BigDecimal.valueOf(250).divide(BigDecimal.valueOf(463), decimalPlace, RoundingMode.HALF_UP))
                        .doubleValue();
            }

            @Override
            public double toLinearUnit(double linear, int decimalPlace, CONVERTER converter) {
                return converter.toKilometres(linear, decimalPlace);
            }
        };

        public abstract double toInch(double linear, int decimalPlace);
        public abstract double toFoot(double linear, int decimalPlace);
        public abstract double toYard(double linear, int decimalPlace);
        public abstract double toKilometres(double linear, int decimalPlace);
        public abstract double toNauticalMiles(double linear, int decimalPlace);
        public abstract double toLinearUnit(double linear, int decimalPlace, CONVERTER converter);

        public static CONVERTER lookUp(String linearStr) throws InvalidOrUnsupportedUnitException {
            try {
                return CONVERTER.valueOf(linearStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                String error = String.format("Invalid/unsupported linear unit: %s", linearStr);
                throw new InvalidOrUnsupportedUnitException(error, e);
            }
        }
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
                double answer = toConverter.toLinearUnit(data, decimalPlace, fromConverter);
                return new Response(request, new GenericPayload(answer));
            } catch (InvalidOrUnsupportedUnitException e) {
                log.warn("Invalid/unsupported linear unit: {}. Converting to supported linear units...", toUnit);
                LinearPayload linearPayload = getLinearPayload(fromConverter, data, decimalPlace);
                return new Response(request, linearPayload);
            }
        } else {
            LinearPayload linearPayload = getLinearPayload(fromConverter, data, decimalPlace);
            return new Response(request, linearPayload);
        }
    }
}

package com.caniksea.poll.ayo.conversion.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * request pojo
 */
@Builder(toBuilder = true) @Getter @ToString
public class Request {
    private String type, fromUnit, toUnit;
    private double data;
    private int decimalPlace;
}

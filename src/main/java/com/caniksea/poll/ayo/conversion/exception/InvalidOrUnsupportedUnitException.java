package com.caniksea.poll.ayo.conversion.exception;

/**
 * custom exception for invalid and/or unsupported unit
 */
public class InvalidOrUnsupportedUnitException extends Exception {
    public InvalidOrUnsupportedUnitException(String error, Throwable throwable) {
        super(error, throwable);
    }
}

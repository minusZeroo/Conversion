package com.caniksea.poll.ayo.conversion.exception;

/**
 * custom exception for unsupported conversion type.
 */
public class UnsupportedConversionTypeException extends Exception {
    public UnsupportedConversionTypeException(String error, Throwable throwable) {
        super(error, throwable);
    }
}

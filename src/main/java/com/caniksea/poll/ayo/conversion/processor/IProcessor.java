package com.caniksea.poll.ayo.conversion.processor;

import com.caniksea.poll.ayo.conversion.exception.InvalidOrUnsupportedUnitException;
import com.caniksea.poll.ayo.conversion.model.Request;
import com.caniksea.poll.ayo.conversion.model.Response;

/**
 * processor interface
 */
public interface IProcessor {
    Response convert(Request request) throws InvalidOrUnsupportedUnitException;
}

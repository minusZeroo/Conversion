package com.caniksea.poll.ayo.conversion.service;

import com.caniksea.poll.ayo.conversion.exception.InvalidOrUnsupportedUnitException;
import com.caniksea.poll.ayo.conversion.exception.UnsupportedConversionTypeException;
import com.caniksea.poll.ayo.conversion.factory.RequestFactory;
import com.caniksea.poll.ayo.conversion.processor.ProcessorFactory;
import com.caniksea.poll.ayo.conversion.processor.IProcessor;
import com.caniksea.poll.ayo.conversion.model.Request;
import com.caniksea.poll.ayo.conversion.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j @Service
public class ConversionService {
    private final RequestFactory requestFactory;

    @Autowired public ConversionService(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    /**
     * perform conversion given request and processor
     * @param processor
     * @param request
     * @return
     * @throws InvalidOrUnsupportedUnitException
     */
    public Response convert(IProcessor processor, Request request) throws InvalidOrUnsupportedUnitException {
        request = this.requestFactory.checkDecimalPlace(request);
        Response response = processor.convert(request);
        return response;
    }

    /**
     * get processor from factory
     * @param conversionType
     * @return
     * @throws UnsupportedConversionTypeException
     */
    public IProcessor getProcessor(String conversionType) throws UnsupportedConversionTypeException {
        ProcessorFactory processorFactory = ProcessorFactory.lookUp(conversionType);
        return processorFactory.getProcessor();
    }
}

package com.caniksea.poll.ayo.conversion.controller;

import com.caniksea.poll.ayo.conversion.exception.InvalidOrUnsupportedUnitException;
import com.caniksea.poll.ayo.conversion.exception.UnsupportedConversionTypeException;
import com.caniksea.poll.ayo.conversion.model.Request;
import com.caniksea.poll.ayo.conversion.model.Response;
import com.caniksea.poll.ayo.conversion.processor.IProcessor;
import com.caniksea.poll.ayo.conversion.service.ConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController @RequestMapping("/api/")
public class ConversionController {
    private final ConversionService service;

    private Response processConversion(IProcessor processor, Request request) {
        try {
            return this.service.convert(processor, request);
        } catch (InvalidOrUnsupportedUnitException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private IProcessor getProcessor(String conversionType) {
        try {
            return this.service.getProcessor(conversionType);
        } catch (UnsupportedConversionTypeException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Autowired public ConversionController(ConversionService service) {
        this.service = service;
    }

    @PostMapping("convert")
    public ResponseEntity convert(@RequestBody Request request) {
        log.info("Conversion request: {}", request);
        String conversionType = request.getType();
        log.info("Getting conversion type...");
        IProcessor processor = getProcessor(conversionType);
        log.info("Conversion type found: {}. Performing conversion operation...", processor.getClass().getSimpleName());
        Response response = processConversion(processor, request);
        log.info("Conversion response: {}", response);
        return ResponseEntity.ok(response);
    }
}

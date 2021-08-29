package com.caniksea.poll.ayo.conversion.processor;

import com.caniksea.poll.ayo.conversion.exception.UnsupportedConversionTypeException;
import com.caniksea.poll.ayo.conversion.processor.impl.LinearProcessor;
import com.caniksea.poll.ayo.conversion.processor.impl.TemperatureProcessor;

/**
 * Processor factory
 */
public enum ProcessorFactory {

    TEMPERATURE {
        @Override
        public IProcessor getProcessor() {
            return new TemperatureProcessor();
        }
    },
    LINEAR {
        @Override
        public IProcessor getProcessor() {
            return new LinearProcessor();
        }
    };

    public abstract IProcessor getProcessor();

    public static ProcessorFactory lookUp(String processor) throws UnsupportedConversionTypeException {
        try {
            return ProcessorFactory.valueOf(processor.toUpperCase());
        } catch (IllegalArgumentException e) {
            String error = String.format("Unsupported conversion type: %s", processor);
            throw new UnsupportedConversionTypeException(error, e);
        }
    }
}

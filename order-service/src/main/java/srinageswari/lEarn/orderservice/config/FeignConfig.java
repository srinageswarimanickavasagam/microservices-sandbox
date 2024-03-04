package srinageswari.lEarn.orderservice.config;

import feign.Capability;
import feign.codec.ErrorDecoder;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import srinageswari.lEarn.orderservice.external.decoder.CustomErrorDecoder;

@Configuration
public class FeignConfig {

    // this is to decode the error messages we receive from the different microservice we are calling within this service
    @Bean
    ErrorDecoder errorDecoder()
    {
        return new CustomErrorDecoder();
    }

    // this is to have the same traceId in the logs for the services we are accessing using feign from this service during the same api request
    @Bean
    public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
}

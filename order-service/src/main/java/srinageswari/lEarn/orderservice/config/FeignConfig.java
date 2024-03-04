package srinageswari.lEarn.orderservice.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import srinageswari.lEarn.orderservice.external.decoder.CustomErrorDecoder;

@Configuration
public class FeignConfig {
    @Bean
    ErrorDecoder errorDecoder()
    {
        return new CustomErrorDecoder();
    }
}

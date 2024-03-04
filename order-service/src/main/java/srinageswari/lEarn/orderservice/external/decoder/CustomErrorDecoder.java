package srinageswari.lEarn.orderservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import srinageswari.lEarn.orderservice.exception.OrderServiceCustomException;
import srinageswari.lEarn.orderservice.external.response.ErrorResponse;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    /**
     * @param s
     * @param response
     * @return
     */
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("::{}",response.request().url());
        log.info("::{}",response.request().headers());
        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new OrderServiceCustomException(errorResponse.getErrorMessage(), errorResponse.getErrorCode(),response.status());
        } catch (IOException e) {
            throw new OrderServiceCustomException("Internel server error","INTERNEL_SERVER_ERROR",500);
        }
    }
}

package srinageswari.lEarn.orderservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import srinageswari.lEarn.orderservice.exception.OrderServiceCustomException;
import srinageswari.lEarn.orderservice.external.request.PaymentRequest;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default ResponseEntity<Long> fallback(PaymentRequest paymentRequest,Throwable e1)
    {
        throw new OrderServiceCustomException("Payment Service is not available","UNAVAILABLE",500);
    }
}

package srinageswari.lEarn.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.lEarn.paymentservice.model.PaymentRequest;
import srinageswari.lEarn.paymentservice.model.PaymentResponse;
import srinageswari.lEarn.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest)
    {
        long id = paymentService.doPayment(paymentRequest);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId)
    {
        return new ResponseEntity<PaymentResponse>(paymentService.getPaymentDetailsByOrderId(orderId),HttpStatus.OK);
    }

}

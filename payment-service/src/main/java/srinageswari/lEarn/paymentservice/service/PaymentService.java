package srinageswari.lEarn.paymentservice.service;

import srinageswari.lEarn.paymentservice.model.PaymentRequest;
import srinageswari.lEarn.paymentservice.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}

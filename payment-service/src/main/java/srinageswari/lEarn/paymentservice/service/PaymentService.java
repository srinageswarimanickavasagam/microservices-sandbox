package srinageswari.lEarn.paymentservice.service;

import srinageswari.lEarn.paymentservice.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}

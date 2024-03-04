package srinageswari.lEarn.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import srinageswari.lEarn.paymentservice.entity.TransactionDetails;
import srinageswari.lEarn.paymentservice.model.PaymentMode;
import srinageswari.lEarn.paymentservice.model.PaymentRequest;
import srinageswari.lEarn.paymentservice.model.PaymentResponse;
import srinageswari.lEarn.paymentservice.repository.PaymentRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;


    /**
     * @param paymentRequest
     * @return
     */
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment details: {}",paymentRequest);
        TransactionDetails transactionDetails = TransactionDetails.builder().paymentDate(Instant.now()).paymentMode(paymentRequest.getPaymentMode().name()).paymentStatus("SUCCESS").orderId(paymentRequest.getOrderId()).referenceNumber(paymentRequest.getReferenceNumber()).amount(paymentRequest.getAmount()).build();
        paymentRepository.save(transactionDetails);
        log.info("Transaction completed with id: {}",transactionDetails.getId());
        return transactionDetails.getId();
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Get payment details for the orderId: {}",orderId);
        TransactionDetails transactionDetails = paymentRepository.findByOrderId(Long.parseLong(orderId));
        return PaymentResponse.builder().paymentId(transactionDetails.getId()).paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode())).paymentDate(transactionDetails.getPaymentDate()).orderId(transactionDetails.getOrderId()).status(transactionDetails.getPaymentStatus()).amount(transactionDetails.getAmount()).build();
    }
}

package srinageswari.lEarn.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srinageswari.lEarn.paymentservice.entity.TransactionDetails;

@Repository
public interface PaymentRepository extends JpaRepository<TransactionDetails, Long> {
    TransactionDetails findByOrderId(long orderId);
}

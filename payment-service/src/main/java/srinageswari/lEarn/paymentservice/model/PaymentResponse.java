package srinageswari.lEarn.paymentservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentResponse {
    private long productId;
    private String productName;
    private long price;
    private long quantity;
}

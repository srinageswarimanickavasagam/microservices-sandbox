package srinageswari.lEarn.paymentservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentServiceCustomException extends RuntimeException{
    private String errorCode;
    public PaymentServiceCustomException(String message, String errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }
}

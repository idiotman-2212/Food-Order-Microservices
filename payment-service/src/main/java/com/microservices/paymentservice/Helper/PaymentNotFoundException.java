package com.microservices.paymentservice.Helper;

import java.io.Serial;

public class PaymentNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public PaymentNotFoundException() {
        super();
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(Throwable cause) {
        super(cause);
    }
}
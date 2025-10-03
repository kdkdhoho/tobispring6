package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws IOException {
        FixedExRateProvider fixedExRateProvider = new FixedExRateProvider();

        PaymentService paymentService = new PaymentService(fixedExRateProvider);
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}

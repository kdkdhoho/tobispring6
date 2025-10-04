package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;

public class PaymentService {

    private final Clock clock;
    private final ExRateProvider exRateProvider;

    public PaymentService(Clock clock, ExRateProvider exRateProvider) {
        this.clock = clock;
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, exRateProvider, clock);
    }
}

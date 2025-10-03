package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;

public class FixedExRatePaymentService extends PaymentService {
    @Override
    BigDecimal getExchangeRateKRW(String currency) throws IOException {
        return BigDecimal.valueOf(1000);
    }

    @Override
    BigDecimal getExchangeRateBonusKRW(String currency) throws IOException {
        return null;
    }
}

package tobyspring.hellospring.exrate;

import java.math.BigDecimal;
import tobyspring.hellospring.payment.ExRateProvider;

public class FixedExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExchangeRate(String currency) {
        return BigDecimal.valueOf(1000);
    }
}

package tobyspring.hellospring;

import java.math.BigDecimal;

public class FixedExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExchangeRate(String currency) {
        return BigDecimal.valueOf(1000);
    }
}

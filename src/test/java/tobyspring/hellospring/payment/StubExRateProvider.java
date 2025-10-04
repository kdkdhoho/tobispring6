package tobyspring.hellospring.payment;

import java.math.BigDecimal;

public class StubExRateProvider implements ExRateProvider {

    private BigDecimal exRate;

    public StubExRateProvider(BigDecimal exRate) {
        this.exRate = exRate;
    }

    @Override
    public BigDecimal getExchangeRate(String currency) {
        return exRate;
    }

    public BigDecimal exRate() {
        return exRate;
    }

    public void setExRate(BigDecimal exRate) {
        this.exRate = exRate;
    }
}

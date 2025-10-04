package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

public class StubExRateProvider implements ExRateProvider {

    private BigDecimal exRate;

    public StubExRateProvider(BigDecimal exRate) {
        this.exRate = exRate;
    }

    @Override
    public BigDecimal getExchangeRate(String currency) throws IOException {
        return exRate;
    }

    public BigDecimal exRate() {
        return exRate;
    }

    public void setExRate(BigDecimal exRate) {
        this.exRate = exRate;
    }
}

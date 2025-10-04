package tobyspring.hellospring;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

//@Component
public class FixedExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExchangeRate(String currency) {
        return BigDecimal.valueOf(1000);
    }
}

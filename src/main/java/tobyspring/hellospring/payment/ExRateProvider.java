package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExRateProvider {

    BigDecimal getExchangeRate(String currency) throws IOException;
}

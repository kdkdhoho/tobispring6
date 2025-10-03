package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;


abstract public class PaymentService {

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRateKRW = getExchangeRateKRW(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRateKRW);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRateKRW, convertedAmount, validUntil);
    }

    abstract BigDecimal getExchangeRateKRW(String currency) throws IOException;
    abstract BigDecimal getExchangeRateBonusKRW(String currency) throws IOException;
}

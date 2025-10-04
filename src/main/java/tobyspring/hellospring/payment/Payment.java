package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class Payment {

    private Long orderId;
    private String currency;
    private BigDecimal foreignCurrencyAmount;
    private BigDecimal exRate;
    private BigDecimal korCurrencyAmount; // Integer도 가능한데 계산이 용이하기 위해 BigDecimal로 통일합니다.
    private LocalDateTime validUntil;

    public Payment(
            Long orderId,
            String currency,
            BigDecimal foreignCurrencyAmount,
            BigDecimal exRate,
            BigDecimal korCurrencyAmount,
            LocalDateTime validUntil
    ) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exRate = exRate;
        this.korCurrencyAmount = korCurrencyAmount;
        this.validUntil = validUntil;
    }

    public static Payment createPrepared(
            Long orderId,
            String currency,
            BigDecimal foreignCurrencyAmount,
            ExRateProvider exRateProvider,
            Clock clock
    ) {
        BigDecimal exRate = exRateProvider.getExchangeRate(currency);

        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now(clock).plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    public boolean isValid(Clock clock) {
        return validUntil.isAfter(LocalDateTime.now(clock));
    }

    public BigDecimal exRate() {
        return exRate;
    }

    public Long orderId() {
        return orderId;
    }

    public String currency() {
        return currency;
    }

    public BigDecimal foreignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal korCurrencyAmount() {
        return korCurrencyAmount;
    }

    public LocalDateTime validUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
               "orderId=" + orderId +
               ", currency='" + currency + '\'' +
               ", foreignCurrencyAmount=" + foreignCurrencyAmount +
               ", exRate=" + exRate +
               ", korCurrencyAmount=" + korCurrencyAmount +
               ", validUntil=" + validUntil +
               '}';
    }
}

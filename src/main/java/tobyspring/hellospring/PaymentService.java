package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Interface를 적용하여 구조를 변경했다.
 * PaymentService는 ExRateProvider의 getExchangeRate() 메서드에만 의존할 뿐이지, 내부 구현체는 관심이 없다.
 * 구현체는 생성자를 통해서 객체가 생성될 때 주입받는 것을 강제하였고, 어떤 구현체를 사용할 것인지는 Client에게 관심사가 던져졌다.
 *
 * Client가 내부 동작을 결정한다는 것은 합리적으로 들릴 수 있다. 그리고 이미 현재 구조로도 충분하다고 생각될 수 있다.</br>
 * 그래도 더 나아간다면, Client가 가지고 있는 구현체를 결정하는 관심사를 분리할 수 있다.</br>
 */
public class PaymentService {
    private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = exRateProvider.getExchangeRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}

package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {

    /**
     * PaymentService는 기능 확장에는 열려있고, 코드 변경에는 닫혀있다. => OCP 원칙 준수 </br>
     * PaymentService는 ExRateProvider Interface의 구현체(FixedExRateProvider, WebApiExRateProvider)에 의존하지 않고, ExRateProvider에만 의존한다. => DIP 원칙 준수 </br>
     * 전략 패턴으로 볼 수도 있다. </br>
     * 과거 코드에서 구현체를 PaymentService가 직접 결정했다. 하지만 Client, 더 나아가 ObjectFactory로 구현체 결정의 권한을 이전했다. => 제어의 역전(IoC)
     */
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

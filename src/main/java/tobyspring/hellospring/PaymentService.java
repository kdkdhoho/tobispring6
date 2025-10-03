package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 환율 정보를 가져오는 메서드를 추상 메서드로 분리했다.</br>
 * 그리고 prepare 메서드를 호출하는 코드를 Client 객체로 옮기고, Client가 직접 원하는 방식으로 환율 정보를 가져올 수 있게 되었다.</br>
 * PaymentService는 더이상 환율 정보를 가져오는 방법이 변경되더라도 코드가 수정될 필요가 없어졌다. Client의 코드만 수정하면 된다.</br>
 * </br>
 * 하지만 상속으로 인한 구조 확장에는 한계가 있다.</br>
 * 상위 클래스를 상속받고 있는 하위 클래스들이, 상위 클래스에 강하게 의존한다.</br>
 * 만약 추상 메서드가 하나 추가된다면, PaymentService를 상속하는 n개의 클래스에서 모두 구현해야 한다.
 * 그리고 단일 상속만 지원하는 언어의 특징도 한계점이 될 수 있다.
 */
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

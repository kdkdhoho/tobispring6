package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 현 테스트 코드의 문제점
 *
 * 1. 우리가 제어할 수 없는 외부 시스템에 문제가 생기면? -> Stub 객체로 해결</br>
 * 2. ExRateProvider가 반환하는 환율 값으로 계산한 것인가? -> Stub 객체로 검증</br>
 * 3. 환율 유효 시간 계산은 정확한가?
 */
class PaymentServiceTest {

    @Test
    @DisplayName("주문번호, 외국 통화 종류, 외국 통화 기준 결제 금액을 전달 받아서 적용 환율, 원화 환산 금액, 원화 환산 금액 유효시간 정보를 합해 Payment 객체를 생성한다.")
    void convertedAmount() throws IOException {
        testAmount(valueOf(500), valueOf(5_000));
        testAmount(valueOf(1_000), valueOf(10_000));
        testAmount(valueOf(3_000), valueOf(30_000));

//        assertThat(result.validUntil()).isAfter(LocalDateTime.now());
//        assertThat(result.validUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        ExRateProvider exRateProvider = new StubExRateProvider(exRate);
        PaymentService paymentService = new PaymentService(exRateProvider);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment.exRate()).isEqualByComparingTo(exRate);
        assertThat(payment.korCurrencyAmount()).isEqualByComparingTo(convertedAmount);
    }
}

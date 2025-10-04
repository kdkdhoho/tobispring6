package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 현 테스트 코드의 문제점
 * <p>
 * 1. 우리가 제어할 수 없는 외부 시스템에 문제가 생기면? -> Stub 객체로 해결</br>
 * 2. ExRateProvider가 반환하는 환율 값으로 계산한 것인가? -> Stub 객체로 검증</br>
 * 3. 환율 유효 시간 계산은 정확한가? -> Clock 객체로 해결
 */
class PaymentServiceTest {
    private Clock clock;

    @BeforeEach
    void setUp() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("주문번호, 외국 통화 종류, 외국 통화 기준 결제 금액을 전달 받아서 적용 환율, 원화 환산 금액, 원화 환산 금액 유효시간 정보를 합해 Payment 객체를 생성한다.")
    void convertedAmount() {
        testAmount(clock, valueOf(500), valueOf(5_000));
        testAmount(clock, valueOf(1_000), valueOf(10_000));
        testAmount(clock, valueOf(3_000), valueOf(30_000));
    }

    private static void testAmount(Clock clock, BigDecimal exRate, BigDecimal convertedAmount) {
        ExRateProvider exRateProvider = new StubExRateProvider(exRate);
        PaymentService paymentService = new PaymentService(clock, exRateProvider);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment.exRate()).isEqualByComparingTo(exRate);
        assertThat(payment.korCurrencyAmount()).isEqualByComparingTo(convertedAmount);

        LocalDateTime now = LocalDateTime.now(clock);
        assertThat(payment.validUntil()).isEqualTo(now.plusMinutes(30));
    }
}

package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestObjectFactory 클래스를 이용하여 스프링 컨테이너를 생성한다.</br>
 * </br>
 * 스프링 컨테이너를 이용해서 PaymentService의 의존성을 주입해줬다.</br>
 * 현재 구조의 장점은, 나중에 ExRateProvider의 동작이 변경되더라도 테스트 코드는 변경 할 필요가 없어진다.</br>
 * 역시나 변경에 대한 사이드 이펙트가 적은 구조인 것이다.</br>
 * </br>
 * 이러한 테스트 방식을 '스프링 컨테이너 테스트'라고 부른다.
 */
@ExtendWith(SpringExtension.class) // JUnit이 @ContextConfiguration 어노테이션을 사용하려면 SpringExtension.class를 추가해야 한다.
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired
    Clock clock;
    @Autowired
    PaymentService paymentService;
    @Autowired
    StubExRateProvider stubExRateProvider;

    @Test
    @DisplayName("주문번호, 외국 통화 종류, 외국 통화 기준 결제 금액을 전달 받아서 적용 환율, 원화 환산 금액, 원화 환산 금액 유효시간 정보를 합해 Payment 객체를 생성한다.")
    void convertedAmount() {
        Payment payment1 = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(payment1.exRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment1.korCurrencyAmount()).isEqualByComparingTo(valueOf(10_000));

        stubExRateProvider.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(payment2.exRate()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.korCurrencyAmount()).isEqualByComparingTo(valueOf(5_000));

        assertThat(payment1.validUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
        assertThat(payment2.validUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }
}

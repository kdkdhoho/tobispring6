package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    private final ExRateProvider exRateProvider = currency -> BigDecimal.valueOf(1_000);

    @Test
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L,
                "USD",
                BigDecimal.valueOf(10),
                exRateProvider,
                clock
        );

        assertThat(payment.korCurrencyAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
        assertThat(payment.validUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValidTest() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L,
                "USD",
                BigDecimal.valueOf(10),
                exRateProvider,
                clock
        );

        assertThat(payment.isValid(clock)).isTrue();

        Clock offset = Clock.offset(
                clock,
                Duration.of(30, ChronoUnit.MINUTES)
        ); // Clock.fixed로 생성한 clock 인스턴스를 기준으로, 30분 이후의 Clock을 생성한다.
        assertThat(payment.isValid(offset)).isFalse();
    }
}

package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    @Test
    @DisplayName("주문번호, 외국 통화 종류, 외국 통화 기준 결제 금액을 전달 받아서 적용 환율, 원화 환산 금액, 원화 환산 금액 유효시간 정보를 합해 Payment 객체를 생성한다.")
    void prepare() throws IOException {
        // given
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider());

        // when
        Payment result = paymentService.prepare(1L, "USD", BigDecimal.valueOf(10));

        // then
        assertThat(result.exRate()).isNotNull();
        assertThat(result.korCurrencyAmount()).isEqualTo(result.foreignCurrencyAmount().multiply(result.exRate()));
        assertThat(result.validUntil()).isAfter(LocalDateTime.now());
        assertThat(result.validUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }
}

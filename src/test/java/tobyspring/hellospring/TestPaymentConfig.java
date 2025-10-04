package tobyspring.hellospring;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.StubExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import static java.math.BigDecimal.valueOf;

@Configuration
public class TestPaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(clock(), exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new StubExRateProvider(valueOf(1_000));
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}

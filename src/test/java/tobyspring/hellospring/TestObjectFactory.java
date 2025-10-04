package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.StubExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import static java.math.BigDecimal.valueOf;

@Configuration
public class TestObjectFactory {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new StubExRateProvider(valueOf(1_000));
    }
}

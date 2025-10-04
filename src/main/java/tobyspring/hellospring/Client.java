package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {
    public static void main(String[] args) throws IOException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class); // 스프링. IoC/DI 컨테이너. 빈 컨테이너. 스프링 컨테이너.
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}

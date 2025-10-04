package tobyspring.hellospring;

import org.springframework.context.annotation.ComponentScan;

// @Component가 붙은 클래스를 스캔해서 스프링 Bean으로 등록하도록 알리는 어노테이션
// @ComponentScan이 붙은 클래스도 스프링 컨테이너에 포함된다.
@ComponentScan
public class ObjectFactory {

    /*@Bean // 스프링 Bean으로 등록한다.
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
//        return new WebApiExRateProvider();
        return new FixedExRateProvider();
    }*/
}

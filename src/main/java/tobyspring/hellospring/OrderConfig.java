package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.order.OrderService;

@Configuration
@Import(DataConfig.class) // DataConfig 설정 정보를 모두 가져옵니다.
public class OrderConfig {

    @Bean
    public OrderService orderService(JpaTransactionManager jpaTransactionManager) {
        return new OrderService(orderRepository(), jpaTransactionManager);
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository();
    }
}

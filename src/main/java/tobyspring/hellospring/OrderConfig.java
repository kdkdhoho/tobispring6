package tobyspring.hellospring;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tobyspring.hellospring.data.JdbcClientOrderRepository;
import tobyspring.hellospring.order.OrderRepository;
import tobyspring.hellospring.order.OrderService;
import tobyspring.hellospring.order.OrderServiceImpl;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {

    @Bean
    public OrderService orderService(OrderRepository orderRepository) {
        return new OrderServiceImpl(orderRepository);
    }

    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcClientOrderRepository(dataSource);
    }
}

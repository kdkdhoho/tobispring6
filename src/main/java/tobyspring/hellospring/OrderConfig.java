package tobyspring.hellospring;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import tobyspring.hellospring.data.JdbcClientOrderRepository;
import tobyspring.hellospring.order.OrderRepository;
import tobyspring.hellospring.order.OrderService;
import tobyspring.hellospring.order.OrderServiceImpl;
import tobyspring.hellospring.order.OrderServiceTxProxy;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {

    @Bean
    public OrderService orderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
        return new OrderServiceTxProxy(new OrderServiceImpl(orderRepository), transactionManager);
    }

    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcClientOrderRepository(dataSource);
    }
}

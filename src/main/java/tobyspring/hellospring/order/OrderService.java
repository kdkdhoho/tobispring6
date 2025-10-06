package tobyspring.hellospring.order;

import java.math.BigDecimal;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

// 애플리케이션 서비스 객체는 외부 기술에 의존하면 안된다.
// 여기서 외부 기술은 Jpa가 해당한다.
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // 문제점 2. JpaTransactionManager에 의존한다.
    private final JpaTransactionManager txManager;

    public OrderService(OrderRepository jpaOrderRepository, JpaTransactionManager txManager) {
        this.orderRepository = jpaOrderRepository;
        this.txManager = txManager;
    }

    public Order createOrder(String orderNumber, BigDecimal totalAmount) {
        Order order = new Order(orderNumber, totalAmount);

        return new TransactionTemplate(txManager).execute(status -> {
            orderRepository.save(order);
            return order;
        });
    }
}

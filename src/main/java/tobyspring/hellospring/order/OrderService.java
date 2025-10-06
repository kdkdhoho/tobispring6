package tobyspring.hellospring.order;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PlatformTransactionManager txManager;

    // 문제점 1. 비즈니스 로직을 담당하는 애플리케이션 서비스 객체에 매번 트랜잭션과 관련된, 기술 관련 코드가 포함된다.
    public OrderService(OrderRepository jpaOrderRepository, PlatformTransactionManager txManager) {
        this.orderRepository = jpaOrderRepository;
        this.txManager = txManager;
    }

    public Order createOrder(String orderNumber, BigDecimal totalAmount) {
        Order order = new Order(orderNumber, totalAmount);

        // 문제점 2. DB에 값을 쓸 때마다 트랜잭션 매니저 코드가 항상 끼게 된다.
        return new TransactionTemplate(txManager).execute(status -> {
            orderRepository.save(order);
            return order;
        });
    }

    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);
    }
}

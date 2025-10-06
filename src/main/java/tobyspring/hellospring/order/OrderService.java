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

    public OrderService(OrderRepository jpaOrderRepository, PlatformTransactionManager txManager) {
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

    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);
    }
}

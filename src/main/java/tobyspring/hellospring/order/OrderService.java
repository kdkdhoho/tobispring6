package tobyspring.hellospring.order;

import java.math.BigDecimal;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.data.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final JpaTransactionManager txManager;

    public OrderService(OrderRepository orderRepository, JpaTransactionManager txManager) {
        this.orderRepository = orderRepository;
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

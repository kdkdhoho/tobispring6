package tobyspring.hellospring.order;

import java.math.BigDecimal;
import java.util.List;
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

        orderRepository.save(order);
        return order;
    }

    public List<Order> createOrders(List<OrderRequest> requests) {
        return new TransactionTemplate(txManager).execute(status -> requests.stream()
                .map(request -> createOrder(request.orderNumber(), request.totalNumber()))
                .toList()
        );
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}

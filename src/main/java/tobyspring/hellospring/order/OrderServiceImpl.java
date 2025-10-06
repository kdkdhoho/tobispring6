package tobyspring.hellospring.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository jpaOrderRepository) {
        this.orderRepository = jpaOrderRepository;
    }

    @Override
    public Order createOrder(String orderNumber, BigDecimal totalAmount) {
        Order order = new Order(orderNumber, totalAmount);
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> createOrders(List<OrderRequest> requests) {
        return requests.stream()
                .map(request -> createOrder(request.orderNumber(), request.totalNumber()))
                .toList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}

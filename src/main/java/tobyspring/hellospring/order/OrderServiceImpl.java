package tobyspring.hellospring.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // @Transactional 어노테이션을 붙임으로써, 스프링은 런타임에 해당 클래스에 트랜잭션 기능을 제공하는 프록시 오브젝트를 추가한다.
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

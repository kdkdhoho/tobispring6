package tobyspring.hellospring.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(String orderNumber, BigDecimal totalAmount);

    List<Order> createOrders(List<OrderRequest> requests);

    Optional<Order> findById(Long id);
}

package tobyspring.hellospring.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.OrderConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(OrderConfig.class)
@ExtendWith(SpringExtension.class)
public class OrderServiceSpringTest {

    @Autowired
    private OrderService orderService;

    @Test
    void createOrderTest() {
        // when
        Order result = orderService.createOrder("0100", BigDecimal.TEN);

        // then
        Optional<Order> found = orderService.findById(result.id());
        assertThat(found).isNotEmpty();
    }

    @Test
    void createOrders_duplicate() {
        // given
        List<OrderRequest> requests = List.of(
                new OrderRequest("0001", BigDecimal.valueOf(1)),
                new OrderRequest("0001", BigDecimal.valueOf(2))
        );

        // when
        // then
        assertThatThrownBy(() -> orderService.createOrders(requests))
                .isInstanceOf(DataIntegrityViolationException.class);
        assertThat(orderService.findById(1L)).isEmpty();
    }
}

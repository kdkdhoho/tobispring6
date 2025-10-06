package tobyspring.hellospring.order;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.OrderConfig;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(result.id()).isGreaterThan(0);
    }
}

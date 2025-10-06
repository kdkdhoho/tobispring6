package tobyspring.hellospring.order;

import java.math.BigDecimal;

public record OrderRequest(
        String orderNumber,
        BigDecimal totalNumber
) {
}

package tobyspring.hellospring.order;

import java.math.BigDecimal;

public class Order {

    private Long id;
    private String orderNumber;
    private BigDecimal totalAmount;

    public Order() {
    }

    public Order(String orderNumber, BigDecimal totalAmount) {
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public String orderNumber() {
        return orderNumber;
    }

    public BigDecimal totalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", orderNumber='" + orderNumber + '\'' +
               ", totalAmount=" + totalAmount +
               '}';
    }
}

package tobyspring.hellospring.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderNumber;

    private BigDecimal totalAmount;

    public Order() {
    }

    public Order(String orderNumber, BigDecimal totalAmount) {
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
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

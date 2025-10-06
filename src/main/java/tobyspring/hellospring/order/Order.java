package tobyspring.hellospring.order;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

// @Entity는 JPA 기술을 사용하는 어노테이션이다. 그런데 컴파일 시점에만 JPA 라이브러리가 필요로 한다.
// 클래스 내 코드에는 JPA 기술과 관련된 내용이 전혀 등장하지 않는다. (어노테이션 제외)
// 만약 JPA를 사용하지 않으면, 런타임에는 JPA에 의존하지 않는다. 즉, JDBC를 사용한다고 하더라도 JPA 관련 어노테이션을 제거하지 않아도 된다.
// 그래도 어노테이션을 제거하고 싶다면 외부 XML 디스크립터를 사용할 수 있다.
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
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

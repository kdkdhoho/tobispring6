package tobyspring.hellospring;

import java.math.BigDecimal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.order.Order;

public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);

        JpaTransactionManager txManager = beanFactory.getBean(JpaTransactionManager.class);

        new TransactionTemplate(txManager).execute(status -> {
            Order order = new Order("100", BigDecimal.TEN);
            orderRepository.save(order);

            System.out.println(order);

            Order order2 = new Order("100", BigDecimal.ONE);
            orderRepository.save(order2);

            return null;
        });
    }
}

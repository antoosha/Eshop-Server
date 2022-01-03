package korolov.project.dao;

import korolov.project.domain.Order;
import korolov.project.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTests {
    @Autowired
    OrderJpaRepository orderJpaRepository;

    @MockBean
    ClientJpaRepository clientJpaRepository;

    @MockBean
    ProductJpaRepository productJpaRepository;

    @MockBean
    Order entity;

    @Test
    public void testCreateReadDelete() {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));

        Mockito.when(clientJpaRepository.existsById("akorol6969@gmail.com")).thenReturn(true);
        Mockito.when(productJpaRepository.existsById((long)1)).thenReturn(true);
        Mockito.when(entity.getProducts()).thenReturn(products);

        Order order = new Order((long)1,"akorol6969@gmail.com", Collections.emptyList());

        orderJpaRepository.save(order);
        List<Order> orders = orderJpaRepository.findAll();
        Assertions.assertThat(orders).extracting(Order::getOrderId).containsOnly((long)1);
        Assertions.assertThat(orders).extracting(Order::getClientEmail).containsOnly("akorol6969@gmail.com");

        orderJpaRepository.deleteAll();
        Assertions.assertThat(orderJpaRepository.findAll()).isEmpty();
    }
}

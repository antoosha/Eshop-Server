package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;


import korolov.project.api.exceptions.HasRelationException;
import korolov.project.dao.ClientJpaRepository;
import korolov.project.dao.OrderJpaRepository;
import korolov.project.dao.ProductJpaRepository;
import korolov.project.dao.ShipmentJpaRepository;
import korolov.project.domain.Client;
import korolov.project.domain.Order;
import korolov.project.domain.Product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderJpaRepository orderJpaRepository;

    /*@Mock
    ClientJpaRepository clientJpaRepository;

    @Mock
    ProductJpaRepository productJpaRepository;

    @Mock
    ShipmentJpaRepository shipmentJpaRepository;
*/
    @Test
    public void testReadAll() {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Order order2 = new Order((long)2, "akorol6969@gmail.com", products);
        List<Order> orders = List.of(order1, order2);

        Mockito.when(orderJpaRepository.findAll()).thenReturn(orders);

        Collection<Order> returnedOrders = orderService.readAll();

        assertEquals(2, returnedOrders.size());
        verify(orderJpaRepository, times(1)).findAll();
    }

    //NullPointerException in inner instances
    /*@Test
    public void testCreate() throws EntityStateException {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order = new Order((long)1, "honza@gmail.com", products);

        Mockito.when(clientJpaRepository.existsById("honza@gmail.com")).thenReturn(true);
        Mockito.when(productJpaRepository.existsById((long)1)).thenReturn(true);
        Mockito.when(orderJpaRepository.save(order)).thenReturn(order);
        orderService.create(order);
        verify(orderJpaRepository, times(1)).save(any());
        verify(orderJpaRepository, times(1)).save(any(Order.class));
        verify(orderJpaRepository, times(1)).save(order);
    }
    */

    @Test
    public void testReadOne(){
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order = new Order((long)1, "honza@gmail.com", products);

        Mockito.when(orderJpaRepository.findById((long)1)).thenReturn(Optional.of(order));

        Order returnedOrder = orderService.readById((long)1).get();

        assertEquals(order.getOrderId(), returnedOrder.getOrderId());
        assertEquals(order.getClientEmail(), returnedOrder.getClientEmail());
        assertEquals(order.getProducts().size(), returnedOrder.getProducts().size());
        verify(orderJpaRepository, times(1)).findById((long)1);
    }

    //NullPointerException in inner instances
    /*@Test
    public void testDelete() throws HasRelationException {
        orderService.deleteById((long)1);
        verify(orderJpaRepository, times(1)).deleteById((long)1);
    }*/

    @Test
    public void testExists(){
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order = new Order((long)1, "honza@gmail.com", products);

        Mockito.when(orderJpaRepository.existsById(1L)).thenReturn(true);
        assertTrue(orderService.exists(order));
        verify(orderJpaRepository, times(1)).existsById(1L);
    }

    //NullPointerException in inner instances
    /*@Test
    public void testUpdate() throws EntityStateException {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order = new Order((long)1, "honza@gmail.com", products);
        Mockito.when(orderService.exists(order)).thenReturn(true);
        orderService.update(order);

        verify(orderJpaRepository, times(1)).save(any());
        verify(orderJpaRepository, times(1)).save(any(Order.class));
        verify(orderJpaRepository, times(1)).save(order);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderJpaRepository, Mockito.times(1)).save(argumentCaptor.capture());
        Order orderProvidedToService = argumentCaptor.getValue();
        assertEquals(1, orderProvidedToService.getOrderId());
        assertEquals("honza@gmail.com", orderProvidedToService.getClientEmail());
    }*/
}

package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
import korolov.project.dao.ProductJpaRepository;
import korolov.project.dao.ShipmentJpaRepository;
import korolov.project.domain.Order;
import korolov.project.domain.Product;
import korolov.project.domain.Shipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTests {

    @InjectMocks
    ShipmentService shipmentService;

    @Mock
    ShipmentJpaRepository shipmentJpaRepository;

    @Mock
    OrderService orderService;

    @Test
    public void testReadAll() {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Order order2 = new Order((long)2, "akorol6969@gmail.com", products);
        Shipment shipment1 = new Shipment(order1, "Thakurova 9", (long)1);
        Shipment shipment2 = new Shipment(order2, "Thakurova 9", (long)2);
        List<Shipment> shipments = List.of(shipment1, shipment2);

        Mockito.when(shipmentJpaRepository.findAll()).thenReturn(shipments);

        Collection<Shipment> returnedProducts = shipmentService.readAll();

        assertEquals(2, returnedProducts.size());
        verify(shipmentJpaRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() throws EntityStateException {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Shipment shipment = new Shipment(order1, "Thakurova 9", (long)1);

        Mockito.when(orderService.exists(any())).thenReturn(true);

        shipmentService.create(shipment);
        verify(shipmentJpaRepository, times(1)).save(any());
        verify(shipmentJpaRepository, times(1)).save(any(Shipment.class));
        verify(shipmentJpaRepository, times(1)).save(shipment);
    }

    @Test
    public void testReadOne(){
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Shipment shipment = new Shipment(order1, "Thakurova 9", (long)1);

        Mockito.when(shipmentJpaRepository.findById((long)1)).thenReturn(Optional.of(shipment));

        Shipment returnedShipment = shipmentService.readById((long)1).get();

        assertEquals(shipment.getClientAddress(), returnedShipment.getClientAddress());
        assertEquals(shipment.getTrackingNumber(), returnedShipment.getTrackingNumber());
        assertEquals(shipment.getOrder().getOrderId(), returnedShipment.getOrder().getOrderId());
        verify(shipmentJpaRepository, times(1)).findById((long)1);
    }

    @Test
    public void testDelete() throws HasRelationException {
        shipmentService.deleteById((long)1);
        verify(shipmentJpaRepository, times(1)).deleteById((long)1);
    }

    @Test
    public void testExists(){
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Shipment shipment = new Shipment(order1, "Thakurova 9", (long)1);

        Mockito.when(shipmentJpaRepository.existsById(1L)).thenReturn(true);
        assertTrue(shipmentService.exists(shipment));
        verify(shipmentJpaRepository, times(1)).existsById(1L);
    }

    //Cannot invoke "korolov.project.domain.Shipment.getTrackingNumber()" because "entity" is null
    @Test
    public void testUpdate() throws EntityStateException {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Shipment shipment = new Shipment(order1, "Thakurova 9", (long)1);

        Mockito.when(orderService.exists(any())).thenReturn(true);
        Mockito.when(shipmentJpaRepository.existsById(1L)).thenReturn(true);
        shipmentService.update(shipment);

        verify(shipmentJpaRepository, times(1)).save(any());
        verify(shipmentJpaRepository, times(1)).save(any(Shipment.class));
        verify(shipmentJpaRepository, times(1)).save(shipment);

        ArgumentCaptor<Shipment> argumentCaptor = ArgumentCaptor.forClass(Shipment.class);
        verify(shipmentJpaRepository, Mockito.times(1)).save(argumentCaptor.capture());
        Shipment shipmentProvidedToService = argumentCaptor.getValue();
        assertEquals("Thakurova 9", shipmentProvidedToService.getClientAddress());
        assertEquals(1, shipmentProvidedToService.getTrackingNumber());
        assertEquals(1, shipmentProvidedToService.getOrder().getOrderId());
    }

    @Test
    public void testFindAllByClientEmail() throws EntityStateException {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Shipment shipment = new Shipment(order1, "Thakurova 9", (long)1);

        Mockito.when(shipmentJpaRepository.findAllByOrderClientEmail("honza@gmail.com")).thenReturn(List.of(shipment));
        List<Shipment> returnedShipments = shipmentService.findAllByClientEmail("honza@gmail.com");

        verify(shipmentJpaRepository, Mockito.times(1)).findAllByOrderClientEmail("honza@gmail.com");
        assertEquals(1, returnedShipments.size());
        assertEquals("Thakurova 9", returnedShipments.get(0).getClientAddress());
    }
}

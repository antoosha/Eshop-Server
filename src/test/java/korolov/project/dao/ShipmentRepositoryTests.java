package korolov.project.dao;

import korolov.project.business.OrderService;
import korolov.project.domain.Order;
import korolov.project.domain.Product;
import korolov.project.domain.Shipment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShipmentRepositoryTests {
    @Autowired
    ShipmentJpaRepository shipmentJpaRepository;

    @MockBean
    OrderService orderService;

    @Test
    public void testCreateReadDelete() {

        Shipment shipment = new Shipment(null,"akorol6969@gmail.com", (long)1);

        Mockito.when(orderService.exists(null)).thenReturn(true);

        shipmentJpaRepository.save(shipment);
        List<Shipment> shipments = shipmentJpaRepository.findAll();
        Assertions.assertThat(shipments).extracting(Shipment::getClientAddress).containsOnly("akorol6969@gmail.com");

        shipmentJpaRepository.deleteAll();
        Assertions.assertThat(shipmentJpaRepository.findAll()).isEmpty();
    }
}

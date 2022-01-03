package korolov.project;

import korolov.project.api.controller.ClientController;
import korolov.project.api.controller.OrderController;
import korolov.project.api.controller.ProductController;
import korolov.project.api.controller.ShipmentController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestServerTests {
    @Autowired
    ClientController clientController;

    @Autowired
    OrderController orderController;

    @Autowired
    ProductController productController;

    @Autowired
    ShipmentController shipmentController;

    @Test
    public void testContextLoadsClientController(){
        Assertions.assertThat(clientController).isNotNull();
    }

    @Test
    public void testContextLoadsOrderController(){
        Assertions.assertThat(orderController).isNotNull();
    }

    @Test
    public void testContextLoadsProductController(){
        Assertions.assertThat(productController).isNotNull();
    }

    @Test
    public void testContextLoadsShipmentController(){
        Assertions.assertThat(shipmentController).isNotNull();
    }
}

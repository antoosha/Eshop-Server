package korolov.project.api.controller;

import korolov.project.business.ShipmentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }
    //CREATE addShipment /* if order is on way*/
    //READ showShipment showAllShipments
    //UPDATE editShipment /*for example, tracking number has been changed*/
    //DELETE deleteShipment /*if client get his/her order*/
}

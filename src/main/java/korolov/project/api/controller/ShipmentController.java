package korolov.project.api.controller;


import korolov.project.api.converter.ShipmentConverter;
import korolov.project.api.dto.ShipmentDTO;
import korolov.project.api.Exceptions.EntityStateException;
import korolov.project.business.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ShipmentConverter shipmentConverter;

    public ShipmentController(ShipmentService shipmentService, ShipmentConverter shipmentConverter) {
        this.shipmentService = shipmentService;
        this.shipmentConverter = shipmentConverter;
    }

    //CREATE addShipment /* if order is on way*/ POST
    @PostMapping("/shipments")
    ShipmentDTO create(@RequestBody ShipmentDTO shipmentDTO) {
        try {
            shipmentDTO = shipmentConverter.fromModel(shipmentService.create(shipmentConverter.toModel(shipmentDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipment already exists or order does not exist.");
        }
        return shipmentDTO;
    }

    //READ showShipment showAllShipments GET
    @GetMapping("/shipments")
    Collection<ShipmentDTO> getAll() {
        return shipmentConverter.fromModels(shipmentService.readAll());
    }

    //READ showShipment GET
    @GetMapping("/shipments/{id}")
    ShipmentDTO getOne(@PathVariable long id) {
        return shipmentConverter.fromModel(shipmentService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found")
        ));
    }

    //UPDATE editShipment /*for example, tracking number has been changed*/ PUT
    @PutMapping("/shipments/{id}")
    ShipmentDTO update(@PathVariable long id, @RequestBody ShipmentDTO shipmentDTO) {
        if (shipmentDTO.getTrackingNumber() != id)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipment ids do not match");
        try {
            shipmentDTO = shipmentConverter.fromModel(shipmentService.update(shipmentConverter.toModel(shipmentDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipment not found");
        }
        return shipmentDTO;
    }

    //DELETE deleteShipment /*if client get his/her order*/ DELETE
    @DeleteMapping("/shipments/{id}")
    void delete(@PathVariable long id) {
        if (shipmentService.readById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipment to delete not found");
        }
        shipmentService.deleteById(id);
    }
}

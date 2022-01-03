package korolov.project.api.controller;

import korolov.project.api.converter.ShipmentConverter;
import korolov.project.api.dto.OrderDTO;
import korolov.project.api.dto.ShipmentDTO;
import korolov.project.api.exceptions.EntityStateException;
import korolov.project.business.OrderService;
import korolov.project.business.ShipmentService;
import korolov.project.domain.Order;
import korolov.project.domain.Product;
import korolov.project.domain.Shipment;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShipmentController.class)
public class ShipmentControllerTests {

    @MockBean
    ShipmentService shipmentService;

    @MockBean
    OrderService orderService;

    @MockBean
    ShipmentConverter shipmentConverter;

    @Autowired
    MockMvc mockMvc;

    //GET ONE
    @Test
    public void testGetOne() throws Exception {
        Product product = new Product("Banana", 10, (long) 1);
        Order order = new Order((long) 1, "akorol6969@gmail.com", List.of(product));
        Shipment shipment = new Shipment(order, "Thakurova 9", (long) 1);
        ShipmentDTO shipmentDTO = new ShipmentDTO((long) 1, "Thakurova 9", (long) 1);

        //For shipment with id 1
        Mockito.when(shipmentService.readById((long) 1)).thenReturn(Optional.of(shipment));
        Mockito.when(shipmentConverter.fromModel(shipment)).thenReturn(shipmentDTO);

        //For existing product
        mockMvc.perform(get("/shipments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderIdDTO", Matchers.is(1)))
                .andExpect(jsonPath("$.clientAddress", Matchers.is("Thakurova 9")))
                .andExpect(jsonPath("$.trackingNumber", Matchers.is(1)));

        //For anything else then id 1
        Mockito.when(shipmentService.readById(not(eq((long) 1)))).thenReturn(Optional.empty());
        //For request to shipment, that does not exist
        mockMvc.perform(get("/shipments/2"))
                .andExpect(status().isNotFound());
    }

    //GET ALL
    @Test
    public void testGetAll() throws Exception {
        List<Product> products = List.of(new Product("Banana", 10, (long) 1));
        Order order1 = new Order((long) 1, "honza@gmail.com", products);
        Order order2 = new Order((long) 2, "akorol6969@gmail.com", products);
        Shipment shipment1 = new Shipment(order1, "Thakurova 9", (long) 1);
        Shipment shipment2 = new Shipment(order2, "Thakurova 9", (long) 2);
        List<Shipment> shipments = List.of(shipment1, shipment2);

        ShipmentDTO shipmentDTO1 = new ShipmentDTO((long) 1, "Thakurova 9", (long) 1);
        ShipmentDTO shipmentDTO2 = new ShipmentDTO((long) 2, "Thakurova 9", (long) 2);
        List<ShipmentDTO> shipmentDTOS = List.of(shipmentDTO1, shipmentDTO2);

        Mockito.when(shipmentService.readAll()).thenReturn(shipments);
        Mockito.when(shipmentConverter.fromModels(shipments)).thenReturn(shipmentDTOS);

        mockMvc.perform(get("/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].trackingNumber", Matchers.is(1)))
                .andExpect(jsonPath("$[1].trackingNumber", Matchers.is(2)));
    }


    //DELETE
    @Test
    public void testDelete() throws Exception {
        List<Product> products = List.of(new Product("Banana", 10, (long) 1));
        Order order = new Order((long) 1, "honza@gmail.com", products);
        Shipment shipment = new Shipment(order, "Thakurova 9", (long) 1);

        //Mock method readById, because it used to verify existence of order before delete.
        Mockito.when(shipmentService.readById(not(eq((long) 1)))).thenReturn(Optional.empty());
        Mockito.when(shipmentService.readById((long) 1)).thenReturn(Optional.of(shipment));

        //If try to delete shipment that does not exist returns HTTP status Not found...
        mockMvc.perform(get("/shipments/2"))
                .andExpect(status().isNotFound());
        //... and deleteById should not be called
        verify(shipmentService, never()).deleteById(any());

        //Try to delete exising shipment, should be OK
        mockMvc.perform(delete("/shipments/1"))
                .andExpect(status().isOk());
        //should be called deleteById
        verify(shipmentService, times(1)).deleteById((long) 1);
    }


    //CREATE EXISTING
    @Test
    public void testCreateExisting() throws Exception {
        //When calling create with any shipment, throw exception.
        doThrow(new EntityStateException()).when(shipmentService).create(any());

        //Try to create order, that already exists, return HTTP status Conflict.
        mockMvc.perform(post("/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderIdDTO\":\"1\",\"clientAddress\":\"Thakurova 9\", \"trackingNumber\":\"1\"}"))
                .andExpect(status().isBadRequest());
    }

    //CREATE NEW
    @Test
    public void testCreate() throws Exception {
        Long l = 1L;
        Product product = new Product("Banana", 10, (long) 1);
        Order order = new Order((long) 1, "akorol6969@gmail.com", List.of(product));
        OrderDTO orderDTO = new OrderDTO((long) 1, "akorol6969@gmail.com", List.of((long) 1));
        Shipment shipment = new Shipment(order, "Thakurova 9", (long) 1);
        ShipmentDTO shipmentDTO = new ShipmentDTO((long) 1, "Thakurova 9", (long) 1);

        Mockito.when(shipmentService.readById(not(eq((long) 1)))).thenReturn(Optional.empty());
        Mockito.when(shipmentService.readById((long) 1)).thenReturn(Optional.of(shipment));
        Mockito.when(shipmentService.create(shipment)).thenReturn(shipment);
        Mockito.when(shipmentService.readById((long) 1)).thenReturn(Optional.of(shipment));
        Mockito.when(shipmentConverter.fromModel(any())).thenReturn(shipmentDTO);
        Mockito.when(shipmentConverter.toModel(any())).thenReturn(shipment);
        Mockito.when(orderService.readById((long) 1)).thenReturn(Optional.of(order));

        mockMvc.perform(post("/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderIdDTO\":\"1\",\"clientAddress\":\"Thakurova 9\", \"trackingNumber\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderIdDTO", Matchers.is(1)))
                .andExpect(jsonPath("$.clientAddress", Matchers.is("Thakurova 9")))
                .andExpect(jsonPath("$.trackingNumber", Matchers.is(1)));

        //Verify using ArgumentCaptor, that create has been called 1x.
        ArgumentCaptor<Shipment> argumentCaptor = ArgumentCaptor.forClass(Shipment.class);
        Mockito.verify(shipmentService, Mockito.times(1)).create(argumentCaptor.capture());
        Shipment shipmentProvidedToService = argumentCaptor.getValue();
        assertEquals(1, shipmentProvidedToService.getTrackingNumber());
        assertEquals("Thakurova 9", shipmentProvidedToService.getClientAddress());
        assertEquals(1, shipmentProvidedToService.getOrder().getOrderId());
    }

    //UPDATE
    @Test
    public void testUpdateNotExisting() throws Exception {
        //When calling create with any shipment, throw exception.
        doThrow(new EntityStateException()).when(shipmentService).update(any());

        //Try to update not existing shipment, return HTTP status NOT FOUND.
        mockMvc.perform(put("/shipments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderIdDTO\":\"1\",\"clientAddress\":\"Thakurova 9\", \"trackingNumber\":\"1\"}"))
                .andExpect(status().isNotFound());
    }

    //UPDATE
    @Test
    public void testUpdate() throws Exception {
        //Create shipment, which should be returned after creating.
        Product product = new Product("Banana", 10, (long) 1);
        Order order = new Order((long) 1, "akorol@gmail.com", List.of(product));
        OrderDTO orderDTO = new OrderDTO((long) 1, "akorol@gmail.com", List.of((long) 1));
        Shipment shipment = new Shipment(order, "Thakurova 9", (long) 1);
        ShipmentDTO shipmentDTO = new ShipmentDTO((long) 1, "Thakurova 9", (long) 1);

        Mockito.when(shipmentService.update(any())).thenReturn(shipment);
        Mockito.when(shipmentConverter.fromModel(shipment)).thenReturn(shipmentDTO);
        Mockito.when(shipmentConverter.toModel(shipmentDTO)).thenReturn(shipment);

        Mockito.when(orderService.readById((long) 1)).thenReturn(Optional.of(order));

        //If try to update shipment, return HTTP OK and client's data
        mockMvc.perform(put("/shipments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderIdDTO\":\"1\",\"clientAddress\":\"Thakurova 9\", \"trackingNumber\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderIdDTO", Matchers.is(1)))
                .andExpect(jsonPath("$.clientAddress", Matchers.is("Thakurova 9")));
    }
}

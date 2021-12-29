package korolov.project.api.controller;

import korolov.project.api.controller.OrderController;
import korolov.project.api.converter.OrderConverter;
import korolov.project.api.converter.ProductConverter;
import korolov.project.api.dto.OrderDTO;
import korolov.project.api.exceptions.EntityStateException;
import korolov.project.business.OrderService;
import korolov.project.business.ProductService;
import korolov.project.dao.ClientJpaRepository;
import korolov.project.dao.ProductJpaRepository;
import korolov.project.domain.Client;
import korolov.project.domain.Order;
import korolov.project.domain.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @MockBean
    OrderService orderService;

    @MockBean
    OrderConverter orderConverter;

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    //GET ONE
    @Test
    public void testGetOne() throws Exception {

        Product product = new Product("Banana", 10, (long)1);
        List<Product> products = List.of(product);
        Order order = new Order((long)1, "akorol6969@gmail.com", products);
        OrderDTO orderDTO = new OrderDTO((long)1, "akorol6969@gmail.com", List.of((long)1));

        Mockito.when(orderService.readById((long)1)).thenReturn(Optional.of(order));
        Mockito.when(orderConverter.fromModel(order)).thenReturn(orderDTO);

        //For existing order
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", Matchers.is(1)))
                .andExpect(jsonPath("$.clientEmail", Matchers.is("akorol6969@gmail.com")))
                .andExpect(jsonPath("$.productIdsDTOs", Matchers.contains(1)));

        Mockito.when(orderService.readById(not(eq((long)1)))).thenReturn(Optional.empty());

        //For request to client, that does not exist
        mockMvc.perform(get("/orders/2"))
                .andExpect(status().isNotFound());
    }

    //GET ALL
    @Test
    public void testGetAll() throws Exception{
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order1 = new Order((long)1, "honza@gmail.com", products);
        Order order2 = new Order((long)2, "akorol6969@gmail.com", products);
        List<Order> orders = List.of(order1, order2);

        OrderDTO orderDTO1 = new OrderDTO((long)1, "honza@gmail.com", List.of((long)1));
        OrderDTO orderDTO2 = new OrderDTO((long)2, "akorol6969@gmail.com", List.of((long)1));
        List<OrderDTO> orderDTOS = List.of(orderDTO1, orderDTO2);

        Mockito.when(orderService.readAll()).thenReturn(orders);
        Mockito.when(orderConverter.fromModels(orders)).thenReturn(orderDTOS);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].clientEmail", Matchers.is("honza@gmail.com")))
                .andExpect(jsonPath("$[1].clientEmail", Matchers.is("akorol6969@gmail.com")));
    }

    //DELETE
    @Test
    public void testDelete() throws Exception {
        List<Product> products = List.of(new Product("Banana", 10, (long)1));
        Order order = new Order((long)1, "honza@gmail.com", products);

        //Mock method readById, because it used to verify existence of order before delete.
        Mockito.when(orderService.readById(not(eq((long)1)))).thenReturn(Optional.empty());
        Mockito.when(orderService.readById((long)1)).thenReturn(Optional.of(order));

        //If try to delete order that does not exist returns HTTP status Not found...
        mockMvc.perform(get("/orders/2"))
                .andExpect(status().isNotFound());
        //... and deleteById should not be called
        verify(orderService, never()).deleteById(any());

        //Try to delete exising order, should be OK
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk());
        //should be called deleteById
        verify(orderService, times(1)).deleteById((long)1);
    }


    //CREATE EXISTING
    @Test
    public void testCreateExisting() throws Exception {
        Product product = new Product("Banana", 10, (long)1);
        Order order = new Order((long)1, "akorol6969@gmail.com",List.of(product));

        //When calling create with any order, throw exception.
        doThrow(new EntityStateException()).when(orderService).create(any());
        Mockito.when(productService.readById((long)1)).thenReturn(Optional.of(product));
        Mockito.when(orderService.readById((long)1)).thenReturn(Optional.of(order));

        //Try to create order, that already exists, return HTTP status Conflict.
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\":\"1\",\"clientEmail\":\"akorol6969@gmail.com\",\"productIdsDTOs\": [1] }"))
                        .andExpect(status().isConflict());
    }

    //CREATE NEW
    @Test
    public void testCreate() throws Exception {
        Product product = new Product("Banana", 10, (long)1);
        Order order = new Order((long)1, "akorol6969@gmail.com", List.of(product));
        OrderDTO orderDTO = new OrderDTO((long)1, "akorol6969@gmail.com", List.of((long)1));

        Mockito.when(orderService.readById(not(eq((long)1)))).thenReturn(Optional.empty());
        Mockito.when(orderService.readById((long)1)).thenReturn(Optional.of(order));
        Mockito.when(orderService.create(order)).thenReturn(order);
        Mockito.when(orderService.readById((long)1)).thenReturn(Optional.of(order));

        Mockito.when(orderConverter.fromModel(any())).thenReturn(orderDTO);
        Mockito.when(orderConverter.toModel(any())).thenReturn(order);
        Mockito.when(productService.readById((long)1)).thenReturn(Optional.of(product));


        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clientEmail\":\"akorol6969@gmail.com\",\"productIdsDTOs\": [1] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", Matchers.is(1)))
                .andExpect(jsonPath("$.clientEmail", Matchers.is("akorol6969@gmail.com")));

        //Verify using ArgumentCaptor, that create has been called 1x.
        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderService, Mockito.times(1)).create(argumentCaptor.capture());
        Order orderProvidedToService = argumentCaptor.getValue();
        assertEquals(1, orderProvidedToService.getOrderId());
        assertEquals("akorol6969@gmail.com",orderProvidedToService.getClientEmail());
        assertEquals(1, orderProvidedToService.getProducts().size());
    }

    //UPDATE
    @Test
    public void testUpdateNotExisting() throws Exception {
        //When calling create with any client, throw exception.
        doThrow(new EntityStateException()).when(orderService).update(any());

        //Try to update not existing order, return HTTP status NOT FOUND.
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\":\"1\",\"clientEmail\":\"akorol6969@gmail.com\",\"productIdsDTOs\": [1] }"))
                .andExpect(status().isNotFound());
    }

    //UPDATE
    @Test
    public void testUpdate() throws Exception {
        //Create shipment, which should be returned after creating.
        Product product = new Product("Banana", 10, (long)1);
        Order order = new Order((long)1, "akorol@gmail.com", List.of(product));
        OrderDTO orderDTO = new OrderDTO((long)1, "akorol@gmail.com", List.of((long)1));

        Mockito.when(orderService.update(any())).thenReturn(order);
        Mockito.when(orderConverter.fromModel(order)).thenReturn(orderDTO);
        Mockito.when(orderConverter.toModel(orderDTO)).thenReturn(order);
        Mockito.when(productService.readById((long)1)).thenReturn(Optional.of(product));

        //If try to update shipment, return HTTP OK and client's data
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\":\"1\",\"clientEmail\":\"akorol@gmail.com\",\"productIdsDTOs\": [1] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", Matchers.is(1)))
                .andExpect(jsonPath("$.clientEmail", Matchers.is("akorol@gmail.com")));
    }
}

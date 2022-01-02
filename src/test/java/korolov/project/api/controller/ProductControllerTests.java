package korolov.project.api.controller;

import korolov.project.api.converter.ProductConverter;
import korolov.project.api.dto.ProductDTO;
import korolov.project.api.exceptions.EntityStateException;
import korolov.project.business.ProductService;
import korolov.project.domain.Client;
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

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    //GET ONE
    @Test
    public void testGetOne() throws Exception {
        Product product = new Product("Banana", 10, (long)1);

        //For product with id 1
        Mockito.when(productService.readById((long)1)).thenReturn(Optional.of(product));

        //For existing product
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", Matchers.is("Banana")))
                .andExpect(jsonPath("$.price", Matchers.is((double)10)))
                .andExpect(jsonPath("$.productId", Matchers.is(1)));

        //For anything else then id 1
        Mockito.when(productService.readById(not(eq((long)1)))).thenReturn(Optional.empty());
        //For request to product, that does not exist
        mockMvc.perform(get("/users/2"))
                .andExpect(status().isNotFound());
    }

    //GET ALL
    @Test
    public void testGetAll() throws Exception{
        Product product1 = new Product("Banana", 10, (long)1);
        Product product2 = new Product("Mango", 15, (long)2);

        List<Product> products = List.of(product1, product2);

        Mockito.when(productService.readAll()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].productName", Matchers.is("Banana")))
                .andExpect(jsonPath("$[1].productName", Matchers.is("Mango")));
    }

    //DELETE
    @Test
    public void testDelete() throws Exception {
        Product product = new Product("Banana", 10, (long)1);

        //Mock method readById, because it used to verify existence of product before delete.
        Mockito.when(productService.readById(not(eq((long)1)))).thenReturn(Optional.empty());
        Mockito.when(productService.readById((long)1)).thenReturn(Optional.of(product));

        //If try to delete product who does not exist returns HTTP status Not found...
        mockMvc.perform(get("/product/2"))
                .andExpect(status().isNotFound());
        //... and deleteById should not be called
        verify(productService, never()).deleteById(any());

        //Try to delete exising user, should be OK
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
        //should be called deleteById
        verify(productService, times(1)).deleteById((long)1);
    }

    //CREATE EXISTING
    @Test
    public void testCreateExisting() throws Exception {
        //When calling create with any product, throw exception.
        doThrow(new EntityStateException()).when(productService).create(any(Product.class));

        //Try to create product, who already exists, return HTTP status Conflict.
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\":\"Banana\",\"price\": \"10\"}"))
                .andExpect(status().isConflict());
    }

    //CREATE NEW
    @Test
    public void testCreate() throws Exception {
        //Create product, which should be returned after creating.
        Product product = new Product("Banana", 10, (long)1);
        ProductDTO productDTO = new ProductDTO("Banana", 10, (long)1);

        Mockito.when(productService.create(product)).thenReturn(product);
        Mockito.when(productService.readById(not(eq((long)1)))).thenReturn(Optional.empty());
        Mockito.when(productService.readById((long)1)).thenReturn(Optional.of(product));

        //If try to create product, return HTTP OK and product's data
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\":\"Banana\",\"price\": \"10\",\"productId\": \"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", Matchers.is("Banana")))
                .andExpect(jsonPath("$.price", Matchers.is((double)10)))
                .andExpect(jsonPath("$.productId", Matchers.is(1)));


        //Verify using ArgumentCaptor, that create has been called 1x.
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productService, Mockito.times(1)).create(argumentCaptor.capture());
        Product productProvidedToService = argumentCaptor.getValue();
        assertEquals("Banana", productProvidedToService.getProductName());
        assertEquals(10, productProvidedToService.getPrice());
        assertEquals(1, productProvidedToService.getProductId());
    }

    //UPDATE
    @Test
    public void testUpdateNotExisting() throws Exception {
        //When calling create with any product, throw exception.
        doThrow(new EntityStateException()).when(productService).update(any(Product.class));


        //Try to update not existing product, return HTTP status NOT FOUND.
        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\":\"Banana\",\"price\": \"10\",\"productId\": \"1\"}"))
                .andExpect(status().isNotFound());
    }

    //UPDATE
    @Test
    public void testUpdate() throws Exception {
        //Create product, which should be returned after creating.
        Product product = new Product("Banana", 10, (long)1);

        Mockito.when(productService.update(product)).thenReturn(product);

        //If try to update product, return HTTP OK and client's data
        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\":\"Banana\",\"price\": \"10\",\"productId\": \"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", Matchers.is("Banana")))
                .andExpect(jsonPath("$.price", Matchers.is((double)10)))
                .andExpect(jsonPath("$.productId", Matchers.is(1)));

        Mockito.when(productService.update(product)).thenReturn(product);

        //Verify using ArgumentCaptor, that create has been called 1x.
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productService, Mockito.times(1)).update(argumentCaptor.capture());
        Product productProvidedToService = argumentCaptor.getValue();
        assertEquals("Banana", productProvidedToService.getProductName());
        assertEquals(10, productProvidedToService.getPrice());
        assertEquals(1, productProvidedToService.getProductId());
    }
}

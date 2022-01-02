package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
import korolov.project.dao.ClientJpaRepository;
import korolov.project.dao.OrderJpaRepository;
import korolov.project.dao.ProductJpaRepository;
import korolov.project.domain.Client;
import korolov.project.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductJpaRepository productJpaRepository;

    @Mock
    OrderJpaRepository orderJpaRepository;

    @Test
    public void testReadAll() {
        Product product1 = new Product("Banana", 10, (long)1);
        Product product2 = new Product("Mango", 15, (long)2);
        List<Product> products = List.of(product1, product2);

        Mockito.when(productJpaRepository.findAll()).thenReturn(products);

        Collection<Product> returnedProducts = productService.readAll();

        assertEquals(2, returnedProducts.size());
        verify(productJpaRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() throws EntityStateException {
        Product product = new Product("Banana", 10, (long)1);
        productService.create(product);
        verify(productJpaRepository, times(1)).save(any());
        verify(productJpaRepository, times(1)).save(any(Product.class));
        verify(productJpaRepository, times(1)).save(product);
    }

    @Test
    public void testReadOne(){
        Product product = new Product("Banana", 10, (long)1);

        Mockito.when(productJpaRepository.findById((long)1)).thenReturn(Optional.of(product));

        Product returnedProduct = productService.readById((long)1).get();

        assertEquals(product.getProductName(), returnedProduct.getProductName());
        assertEquals(product.getProductId(), returnedProduct.getProductId());
        assertEquals(product.getPrice(), returnedProduct.getPrice());
        verify(productJpaRepository, times(1)).findById((long)1);
    }

    @Test
    public void testDelete() throws HasRelationException {
        Mockito.when(orderJpaRepository.findAll()).thenReturn(Collections.emptyList());
        productService.deleteById((long)1);
        verify(productJpaRepository, times(1)).deleteById((long)1);
    }

    @Test
    public void testExists(){
        Product product = new Product("Banana", 10, (long)1);
        Mockito.when(productJpaRepository.existsById(1L)).thenReturn(true);
        assertTrue(productService.exists(product));
        verify(productJpaRepository, times(1)).existsById(1L);
    }

    @Test
    public void testUpdate() throws EntityStateException {
        Product product = new Product("Banana", 10, (long)1);
        Mockito.when(productService.exists(product)).thenReturn(true);
        productService.update(product);

        verify(productJpaRepository, times(1)).save(any());
        verify(productJpaRepository, times(1)).save(any(Product.class));
        verify(productJpaRepository, times(1)).save(product);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productJpaRepository, Mockito.times(1)).save(argumentCaptor.capture());
        Product productProvidedToService = argumentCaptor.getValue();
        assertEquals(1, productProvidedToService.getProductId());
        assertEquals("Banana", productProvidedToService.getProductName());
        assertEquals(10, productProvidedToService.getPrice());
    }

    @Test
    public void testFindAllWithPriceLessThanEqual() throws EntityStateException {
        Product product = new Product("Banana", 10, (long)1);
        Mockito.when(productJpaRepository.findAllByPriceIsLessThanEqual(10)).thenReturn(List.of(product));
        List<Product> returnedProducts = productService.findAllWithPriceLessThanEqual(10);

        assertEquals(1, returnedProducts.size());
        assertEquals("Banana", returnedProducts.get(0).getProductName());
        assertEquals(10, returnedProducts.get(0).getPrice());
    }
}

package korolov.project.api.controller;

import korolov.project.business.ProductService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //CREATE createProduct or addProduct POST

    //READ showProduct showAllProducts GET

    //UPDATE editProduct PUT

    //DELETE deleteProduct hideProduct/*if we dont want to delete but want to hide from all clients*/ DELETE

    //TODO implement controller
}

package org.example.products_spring.controller;


import lombok.AllArgsConstructor;
import org.example.products_spring.exception.NotFoundByIDException;
import org.example.products_spring.model_save.ProductSave;
import org.example.products_spring.models.Product;
import org.example.products_spring.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductSave product) {
        logger.info("Saving product " + product);
        productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product with name" + product.getProductName() +
                " saved successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam(value = "id", required = true) int id) {
        logger.info("Deleting product with id" + id);
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product with id " + id + " deleted successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestParam(value = "id", required = true) int id, @RequestBody ProductSave product) {
        try {
            logger.info("Updating product with id" + id);
            Product product1 = productService.updateProduct(product, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(product1);
        }catch (NotFoundByIDException e) {
            logger.warning("Product with id" + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getById(@RequestParam(value = "id", required = true) int id) {
        try {
            logger.info("Getting product with id " + id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.getById(id));
        }catch (NotFoundByIDException e) {
            logger.warning("Product with id" + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/get_all")
    public List<Product> getAllProducts(){
        logger.info("Getting all products");
        return productService.getAll();
    }


}

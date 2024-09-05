package org.example.products_spring.service;


import lombok.AllArgsConstructor;
import org.example.products_spring.dao.ProductRepository;
import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.exception.NotFoundByIDException;
import org.example.products_spring.model_save.ProductSave;
import org.example.products_spring.models.Product;
import org.springframework.stereotype.Service;
import org.example.products_spring.aspects.annotation.toCheckProduct;
import java.util.List;
import java.util.logging.Logger;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    public void createProduct(Product product){
        logger.info("Creating product with id: " + product.getProductId());
        productRepository.save(product);
        logger.info("Product created");
    }

    public Product getById(int id){
        logger.info("Retrieving product with id: " + id);
        return productRepository.findById(id).get();
    }



    public void updateProduct(Product product, int id){
        logger.info("Updating product with id: " + id);
        Product productToSave = productRepository.findById(id).get();
        productToSave.setProductName(product.getProductName());
        productToSave.setDescription(product.getDescription());
        productToSave.setPrice(product.getPrice());
        productRepository.save(productToSave);
    }

    @toCheckProduct
    public void deleteProduct(int id){
        logger.info("Deleting product with id: " + id);
       productRepository.deleteById(id);

    }


    public List<Product> getAll(){
        return productRepository.findAll();
    }



}

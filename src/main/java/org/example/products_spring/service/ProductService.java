package org.example.products_spring.service;


import org.example.products_spring.dao.ProductDao;
import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.exception.NotFoundByIDException;
import org.example.products_spring.model_save.ProductSave;
import org.example.products_spring.models.Product;
import org.springframework.stereotype.Service;
import org.example.products_spring.aspects.annotation.toCheckProduct;
import java.util.List;
import java.util.logging.Logger;


@Service
public class ProductService {

    private ProductDao productDao;
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    public void createProduct(ProductSave productSave){
        Product product = new Product();
        product.setProductName(productSave.getProductName());
        product.setDescription(productSave.getDescription());
        product.setPrice(productSave.getPrice());
        try {
            boolean result = productDao.create(product);
            if (!result){
                throw new ExceptionCreate("product");
            }
            logger.info("Product created successfully");
        }catch (ExceptionCreate ex){
            ex.getMessage();
        }
    }

    public Product getById(int id) throws NotFoundByIDException{
        // Обработка исключения где ID не существует
        try {
            Product product = productDao.getById(id);
            if (product==null){
                throw new NotFoundByIDException("product", id);
            }
            return product;
        }catch (NotFoundByIDException ex){
            logger.info(ex.getMessage());
        }
        return null;
    }



    public Product updateProduct(ProductSave productSave, int id) throws NotFoundByIDException{
        try {
            Product product = productDao.getById(id);
            if (product==null){
                throw new NotFoundByIDException("product", id);
            }
            product.setProductName(productSave.getProductName());
            product.setDescription(productSave.getDescription());
            product.setPrice(productSave.getPrice());
            productDao.updateProduct(product);
            return product;
        }catch (NotFoundByIDException ex){
            logger.info(ex.getMessage());
        }
        return null;
    }

    @toCheckProduct
    public void deleteProduct(int id){
        // Обработка исключения где ID не существует
        try {
            productDao.deleteProduct(id);
        }catch (NotFoundByIDException ex){
            logger.info(ex.getMessage());
        }

    }


    public List<Product> getAll(){
        return productDao.getAll();
    }



}

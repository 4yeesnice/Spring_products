package org.example.products_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ProductsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsSpringApplication.class, args);
    }

}

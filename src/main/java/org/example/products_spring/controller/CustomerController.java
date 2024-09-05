package org.example.products_spring.controller;


import lombok.AllArgsConstructor;
import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.model_save.Customer_save;
import org.example.products_spring.models.Customer;
import org.example.products_spring.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("customer")
@AllArgsConstructor
public class CustomerController {


    private final CustomerService customerService;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @PostMapping("/save")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        try{
            customerService.saveCustomer(customer);
            logger.info("Customer saved successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Customer saved successfully");
        } catch(ExceptionCreate e){
            System.out.println("ExceptionCreate");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestParam(value = "id", required = true) int id){
        logger.info("Customer updated successfully");
        customerService.updateCustomer(customer, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam(value = "id", required = true) int id){
        logger.info("Customer deleted successfully");
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/get_by_id")
    public ResponseEntity<Customer> getCustomerById(@RequestParam(value = "id", required = true) int id){
        logger.info("Customer retrieved successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.getById(id));
    }

    @GetMapping("/get_all")
    public List<Customer> getAllCustomer(){
        logger.info("Printing all customers");
        return customerService.getAll();
    }
}

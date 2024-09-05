package org.example.products_spring.service;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import org.example.products_spring.dao.CustomerRepository;
import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.exception.NotFoundByIDException;
import org.example.products_spring.model_save.Customer_save;
import org.example.products_spring.models.Customer;
import org.springframework.stereotype.Service;
import org.example.products_spring.aspects.annotation.toCheck;

@Service
@AllArgsConstructor
public class CustomerService {

    private final Logger logger = Logger.getLogger(CustomerService.class.getName());
    private final CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) throws ExceptionCreate{
        customerRepository.save(customer);
    }

    public Customer getById(int id){
        return customerRepository.findById(id).get();
    }

    public void updateCustomer(Customer customer, int id){
        Customer customerToUpdate = customerRepository.findById(id).get();
        customerToUpdate.setCustomerName(customer.getCustomerName());
        customerToUpdate.setContactInfo(customer.getContactInfo());
        customerRepository.save(customerToUpdate);
    }

    @toCheck
    public void deleteCustomerById(int id){
        customerRepository.deleteById(id);
    }


    // Метод выводит всех пользователей
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

}

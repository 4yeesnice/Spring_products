package org.example.products_spring.service;


import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import org.example.products_spring.dao.CustomerDao;
import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.exception.NotFoundByIDException;
import org.example.products_spring.model_save.Customer_save;
import org.example.products_spring.models.Customer;
import org.springframework.stereotype.Service;
import org.example.products_spring.aspects.annotation.toCheck;

@Service
public class CustomerService {

    private final Logger logger = Logger.getLogger(CustomerService.class.getName());
    private CustomerDao customerDao;

    public void saveCustomer(Customer_save Customer_save) throws ExceptionCreate{
        Customer customer = new Customer();
        customer.setCustomerName(Customer_save.getCustomerName());
        customer.setContactInfo(Customer_save.getContactInfo());
        // Проверка создался ли обьект
        boolean result = customerDao.create(customer);

    }

    public Customer getById(int id){
        // Обработка исключения где ID не существует
        try {
            Customer customer = customerDao.getById(id);
            if (customer==null){
                throw new NotFoundByIDException("customer", id);
            }
            return customer;
        }catch (NotFoundByIDException ex){
            logger.info(ex.getMessage());
        }

        return null;
    }

    public void updateCustomer(Customer_save customer_save, int id){

        // Обработка исключения где ID не существует
        try {
            Customer customer = customerDao.getById(id);
            if (customer==null){
                throw new NotFoundByIDException("customer", id);
            }
            customer.setCustomerName(customer_save.getCustomerName());
            customer.setContactInfo(customer_save.getContactInfo());
            customerDao.updateCustomer(customer);
            logger.info("Customer updated!");
        } catch (NotFoundByIDException e) {
            System.out.println(e.getMessage());
        }
    }

    @toCheck
    public void deleteCustomerById(int id){
        // Обработка исключения где ID не существует
        try {
            logger.info("Delete customer by id: " + id);
            customerDao.deleteCustomer(id);
            logger.info("Customer with id: " + id + "deleted");
        } catch (NotFoundByIDException e) {
            System.out.println(e.getMessage());
        }
    }


    // Метод выводит всех пользователей
    public List<Customer> getAll(){
        return customerDao.getAll();
    }

}

package org.example.products_spring.model_save;

import org.example.products_spring.models.Customer;
import org.example.products_spring.models.Product;

import java.sql.Date;
import java.util.List;

public class OrderSave {
    private String orderStatus;
    private Date date;
    private Customer customer;
    private List<Product> productList;
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }


}

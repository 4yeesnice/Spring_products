package org.example.products_spring.service;



import lombok.AllArgsConstructor;

import org.example.products_spring.dao.OrderRepository;

import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.models.Order;
import org.springframework.stereotype.Service;
import org.example.products_spring.aspects.annotation.toCheckOrder;
import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class OrderService {


    private final Logger logger = Logger.getLogger(OrderService.class.getName());
    private final OrderRepository orderRepository;
    // Создание заказа
    public void createOrder(Order order) throws ExceptionCreate {
        logger.info("Creating order");
        orderRepository.save(order);
    }

    // Показать заказ по ID
    public Order getOrderById(int id) {
        logger.info("Getting order by id: " + id);
        return orderRepository.findById(id).get();
    }


    // Обновляет заказ
    public void updateOrder(Order order, int id){
        logger.info("Updating order by id: " + id);
        Order orderToSave = orderRepository.findById(id).get();
        orderToSave.setOrderStatus(order.getOrderStatus());
        orderToSave.setProductList(order.getProductList());
        orderToSave.setDate(order.getDate());
        orderRepository.save(orderToSave);
    }

    @toCheckOrder
    // Удаляет заказ по ID
    public void deleteOrder(int id) throws ExceptionCreate {
        logger.info("Deleting order by id: " + id);
        orderRepository.deleteById(id);
    }

    // Выводит все заказы
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}


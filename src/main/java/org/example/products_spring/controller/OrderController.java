package org.example.products_spring.controller;


import lombok.AllArgsConstructor;
import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.model_save.OrderSave;
import org.example.products_spring.models.Order;
import org.example.products_spring.service.OrderService;
import org.example.products_spring.service.ProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Logger logger = Logger.getLogger(OrderController.class.getName());

    @PostMapping("/save")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            logger.info("Creating order: " + order);
            orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (ExceptionCreate e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Order> getById(@RequestParam(value = "id") int id) {
        logger.info("Get Order by id: " + id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.getOrderById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestBody Order order, @RequestParam(value = "id") int id) {
            logger.info("Updating order with id " + order.getOrderId());
            orderService.updateOrder(order, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Order updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestParam(value = "id") int id) {
        try {
            logger.info("Deleting order with id " + id);
            orderService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Order deleted successfully");
        } catch (ExceptionCreate e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get_all")
    public List<Order> getAllOrders(){
        logger.info("Getting all products");
        return orderService.getAll();
    }

}

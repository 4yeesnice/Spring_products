package org.example.products_spring.service;



import org.example.products_spring.dao.CustomerDao;
import org.example.products_spring.dao.OrderDao;
import org.example.products_spring.dao.ProductDao;
import org.example.products_spring.exception.ExceptionCreate;
import org.example.products_spring.exception.NotFoundByIDException;
import org.example.products_spring.model_save.OrderSave;
import org.example.products_spring.models.Customer;
import org.example.products_spring.models.Order;
import org.springframework.stereotype.Service;
import org.example.products_spring.aspects.annotation.toCheckOrder;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderService {

    private OrderDao orderDao;
    private CustomerDao customerDao;

    private final Logger logger = Logger.getLogger(OrderService.class.getName());

    // Создание заказа
    public void createOrder(OrderSave orderSave) throws ExceptionCreate {
        Order order = new Order();
        order.setOrderStatus(orderSave.getOrderStatus());
        // Проверка потребителя по ID
        try {
            Customer customer = customerDao.getById(orderSave.getCustomer().getCustomerId());
            if (customer == null) {
                throw new NotFoundByIDException("customer", orderSave.getCustomer().getCustomerId());
            }
            order.setCustomer(customer);
        } catch (NotFoundByIDException ex) {
            logger.info(ex.getMessage());
        }
        // Устанавлиаем дату заказа
        order.setDate(orderSave.getDate());
        order.setProductList(orderSave.getProductList());
        // Проверяем сможем ли мы создать
        try {
            orderDao.create(order);
        } catch (ExceptionCreate ex) {
            logger.info(ex.getMessage());
        }
    }

    // Показать заказ по ID
    public Order getOrderById(int id) {
        Order order = null;
        // Проверка на ID
        try {
            order = orderDao.getById(id);
            return order;
        } catch (NotFoundByIDException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }


    // Обновляет заказ
    public void updateOrder(int id, OrderSave orderSave) throws ExceptionCreate {
        // Обработка исключения где ID не существует
        try {
            Order order = orderDao.getById(id);
            order.setOrderStatus(orderSave.getOrderStatus());
            order.setDate(orderSave.getDate());
            order.setProductList(orderSave.getProductList());
            orderDao.updateOrder(order);
        } catch (NotFoundByIDException e) {
            logger.info(e.getMessage());
        }
    }

    @toCheckOrder
    // Удаляет заказ по ID
    public void deleteOrder(int id) throws ExceptionCreate {
        // Обработка исключения где ID не существует
        try {
            orderDao.deleteOrder(id);
            logger.info("Order with id: " + id + " is deleted");
        } catch (NotFoundByIDException ex) {
            logger.info(ex.getMessage());
        }
    }

    // Выводит все заказы
    public List<Order> getAll() {
        return orderDao.getAll();
    }
}


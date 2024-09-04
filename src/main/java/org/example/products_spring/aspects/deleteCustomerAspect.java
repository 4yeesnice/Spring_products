package org.example.products_spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.products_spring.models.Customer;
import org.example.products_spring.utils.DbConnector;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Aspect
@Component
public class deleteCustomerAspect {
    Logger logger = Logger.getLogger(deleteCustomerAspect.class.getName());
    Connection connection = null;

    @Around("@annotation(org.example.products_spring.aspects.annotation.toCheck)")
    public void deleteCustomer(ProceedingJoinPoint joinPoint) throws Throwable {
        DbConnector connector = new DbConnector();
        this.connection = connector.getConnection();

        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Aspect running for " + name);

        for (Object arg : args) {
            if (arg instanceof Integer) {
                try{
                    // Поиск всех заказов с данным айди
                    Integer id_customer = (Integer) arg;
                    String sql2 = "select * from orders where customer_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                    preparedStatement.setInt(1, id_customer);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // удаление заказов из заказ продуктов
                    while (resultSet.next()) {
                        int id_order = resultSet.getInt("order_id");
                        String sql3 = "delete from orders_products where order_id = ?";
                        PreparedStatement preparedStatement2 = connection.prepareStatement(sql3);
                        preparedStatement2.setInt(1, id_order);
                        preparedStatement2.execute();
                    }
                    // удаление самих заказов по данному айди
                    // P.S появилась мысль сделать inner join и удалить все вместе за 1 запрос
                    String sql4 = "delete from orders where customer_id = ?";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(sql4);
                    preparedStatement3.setInt(1, id_customer);
                    preparedStatement3.execute();

                    joinPoint.proceed();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

}

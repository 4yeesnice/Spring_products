package org.example.products_spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.products_spring.utils.DbConnector;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

@Aspect
@Component
public class deleteOrderAspect {
    Logger logger = Logger.getLogger(deleteOrderAspect.class.getName());
    Connection connection = null;

    @Around("@annotation(org.example.products_spring.aspects.annotation.toCheckOrder)")
    public void deleteOrder(ProceedingJoinPoint joinPoint) throws Throwable {
        DbConnector connector = new DbConnector();
        this.connection = connector.getConnection();

        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Aspect running for " + name);

        for (Object arg : args) {
            if (arg instanceof Integer) {
                try{
                    // Поиск всех заказов с данным айди
                    Integer id_product = (Integer) arg;
                    String sqlQuery = "delete from orders_products where order_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setInt(1, id_product);
                    preparedStatement.execute();
                    // P.S появилась мысль сделать inner join и удалить все вместе за 1 запрос
                    // P.S не получится за 1 присест сделать. Нужно было при составлении таблиц сделать ->
                    // -> FOREIGN KEY (product_id) REFERENCES products (product_id) ! ON DELETE CASCADE !

                    joinPoint.proceed();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }
}

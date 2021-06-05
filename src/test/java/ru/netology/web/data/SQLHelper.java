package ru.netology.web.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.web.sqlentities.CreditRequestEntity;
import ru.netology.web.sqlentities.OrderEntity;
import ru.netology.web.sqlentities.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");
    private static Connection conn;

    private static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void cleanDb() {
        val runner = new QueryRunner();
        val creditRequest = "DELETE FROM credit_request_entity";
        val order = "DELETE FROM order_entity";
        val payment = "DELETE FROM payment_entity";

        try (val conn = getConnection()) {
            runner.update(conn, creditRequest);
            runner.update(conn, order);
            runner.update(conn, payment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PaymentEntity getStatusForPaymentWithDebitCard() {
        val extractStatus = "SELECT * FROM payment_entity";
        val runner = new QueryRunner();
        try (val conn = getConnection()) {
            val paymentRecord = runner.query(conn, extractStatus, new BeanHandler<>(PaymentEntity.class));
            return paymentRecord;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CreditRequestEntity getStatusForPaymentWithCreditCard() {
        val extractStatus = "SELECT * FROM credit_request_entity";
        val runner = new QueryRunner();
        try (val conn = getConnection()) {
            val creditCardStatus = runner.query(conn, extractStatus, new BeanHandler<>(CreditRequestEntity.class));
            return creditCardStatus;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OrderEntity getOrderPaymentId() {
        val extractPaymentId = "SELECT * FROM order_entity";
        val runner = new QueryRunner();
        try (val conn = getConnection()) {
            val orderRecord = runner.query(conn, extractPaymentId, new BeanHandler<>(OrderEntity.class));
            return orderRecord;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


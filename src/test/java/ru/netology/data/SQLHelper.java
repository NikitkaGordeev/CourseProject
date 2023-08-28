package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");
    @SneakyThrows
    public static void cleanBD() {
        val deletePaymentEntity = "DELETE FROM payment_entity ";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, deletePaymentEntity);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @SneakyThrows
    public static String getPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity";
        return getStatus(statusSQL);
    }
    @SneakyThrows
    private static String getStatus(String query) {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            String status = runner.query(conn, query, new ScalarHandler<String>());
            return status;
        }
    }
}

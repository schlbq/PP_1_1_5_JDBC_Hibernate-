package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соединения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static Connection connection = null;

    public static Connection getConnection() {

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection - Failed");
        }
        return connection;
    }
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/hibernate_db?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");

                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create");

                sessionFactory = new Configuration()
                        .addProperties(settings)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();

                System.out.println("Connection SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection ERROR");
            }
        }
        return sessionFactory;
    }
    public static Connection closeConnectionHibernate() {
        try {
            if (connection != null) { // checked the status of object
                getConnection().close();
                System.out.println("Connection CLOSE");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}

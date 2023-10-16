package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users
                    (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        firstName VARCHAR(20) NOT NULL,
                        lastName VARCHAR(30) NOT NULL,
                        age TINYINT NULL
                    );""");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (firstName, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("Пользователь с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, firstName, lastName, age FROM users");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }
}
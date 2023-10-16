package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import static jm.task.core.jdbc.util.Util.closeConnectionHibernate;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte)26);
        userService.saveUser("Petr", "Petrov", (byte)43 );
        userService.saveUser("Zaur", "Tregulov", (byte)42);
        userService.saveUser("Artem", "Shrtaukh", (byte)26);

        userService.removeUserById(1);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        closeConnectionHibernate();
    }
}

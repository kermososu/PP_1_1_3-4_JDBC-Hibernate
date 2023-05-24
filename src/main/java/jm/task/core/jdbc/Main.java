package jm.task.core.jdbc;

import jm.task.core.jdbc.service.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {


        UserService user = new UserServiceImpl();

        user.createUsersTable();

        user.saveUser("Фёдор", "Скворцов", (byte) 43);
        user.saveUser("Александра", "Жукова", (byte) 25);
        user.saveUser("Дарья", "Лопатина", (byte) 15);
        user.saveUser("Маргарита", "Иванова", (byte) 97);

        System.out.println(user.getAllUsers());

        user.cleanUsersTable();
        user.dropUsersTable();

    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement s = null;
        try (Statement statement = Util.connect().createStatement()) {
            statement.executeUpdate("CREATE TABLE Users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)");
        } catch (SQLException e) {
            if (s != null)
            System.out.println("не создалась");
        }


    }

    public void dropUsersTable() {
        Statement stat = null;
        try (Statement statement = Util.connect().createStatement()) {

            statement.executeUpdate("DROP TABLE Users");
        } catch (SQLException e) {
            if (stat != null) {
            System.out.println("не удалилась");
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement statement = null;
        try (PreparedStatement stat = Util.connect().prepareStatement("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)")){

            stat.setString(1, name);
            stat.setString(2, lastName);
            stat.setInt(3, age);

            stat.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            if (statement != null)
            System.out.println("не добавилось");
        }

    }

    public void removeUserById(long id) {
        PreparedStatement s = null;
        try (PreparedStatement stat = Util.connect().prepareStatement("delete from users where id = " + id)) {

            stat.executeUpdate();

        } catch (SQLException e) {
            if (s != null)
            System.out.println("не удалился по айди");
        }

    }

    public List<User> getAllUsers() {
        Statement s = null;
        List <User> list = new ArrayList<>();
        try (Statement statement = Util.connect().createStatement()) {

            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            if (s != null)
            System.out.println("не получилось");
        }

        return list;
    }

    public void cleanUsersTable() {
        Statement s = null;
        try (Statement statement = Util.connect().createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            if (s != null)
            System.out.println("таблицы не очистилась");
        }
    }
}

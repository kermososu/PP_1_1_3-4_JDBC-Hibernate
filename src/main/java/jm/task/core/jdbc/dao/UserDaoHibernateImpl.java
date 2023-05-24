package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory = Util.hibernateConnect();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            User temp = new User(name, lastName, age);
            session.save(temp);
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.createSQLQuery("delete from user where id = " + id).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> list = new ArrayList<>();
        try (session) {

            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);

            list = session.createQuery(criteriaQuery).getResultList();

            transaction.commit();
            return list;
        } catch (HibernateException e) {
            System.out.println("Asd");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;

    }

    @Override
    public void cleanUsersTable() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.createSQLQuery("TRUNCATE TABLE User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}

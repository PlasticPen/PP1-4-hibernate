package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("""
                    CREATE TABLE IF NOT EXISTS test.users
                    (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name varchar(30),
                        last_name varchar(30),
                        age INT
                    );
                    """).executeUpdate();
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            } throw new RuntimeException(he);
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS test.users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            } throw new RuntimeException(he);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User where id = " + id).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            } throw new RuntimeException(he);

        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        List<User> users;
        try (Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();

            users = session.createQuery("from User")
                    .getResultList();
            session.getTransaction().commit();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("truncate table test.users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String REMOVE_USER_BY_ID_SQL = """
            DELETE FROM test.users
            WHERE id = ?
            """;

    private static final String SAVE_USER_SQL = """
            INSERT INTO test.users (name, lastName, age)
            VALUES (?, ?, ?);
            """;

    private static final String GET_ALL_USERS_SQL = """
            SELECT id, name, lastName, age
            FROM test.users;
            """;

    private static final String CLEAN_USERS_TABLE_SQL = """
            TRUNCATE TABLE test.users;
            """;

    private static final String DROP_USERS_TABLE = """
            DROP TABLE IF EXISTS test.users;
            """;

    private static final String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS test.users
            (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name varchar(30),
                lastName varchar(30),
                age INT
            );
            """;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERS_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_USERS_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_USERS_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

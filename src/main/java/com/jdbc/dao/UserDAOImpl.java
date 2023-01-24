package com.jdbc.dao;

import com.jdbc.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDAOImpl implements UserDAO {


    private final String DATASOURCE_URL = "jdbc:postgresql://localhost:5432/jdbcTEST";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "admin";

    @Override
    public void save(User user) {
        String query = "insert into users (firstName, lastName) values (?,?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATASOURCE_URL, USERNAME, PASSWORD);
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());

            int out = statement.executeUpdate();
            if (out != 0) {
                System.out.println("User saved");
            } else System.out.println("User save failed with id=" + user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(statement).close();
                Objects.requireNonNull(connection).close();
            } catch (SQLException e ) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public List<User> getAll() {
        String query = "select id, firstName, lastName from users";
        List<User> userList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATASOURCE_URL, USERNAME, PASSWORD);
            statement = connection.prepareStatement(query);
            set = statement.executeQuery();
            while (set.next()) {
                User user = new User();
                user.setId(set.getLong("id"));
                user.setFirstName(set.getString("firstname"));
                user.setLastName(set.getString("lastname"));
                userList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(statement).close();
                Objects.requireNonNull(set).close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userList;
    }
}

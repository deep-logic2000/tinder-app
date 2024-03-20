package org.tinder.dao;

import org.tinder.User;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CollectionUserDAO implements UserDAO, Serializable {

    private List<User> databaseOfUsers = new ArrayList<>();


    private static final String GET_ALL_USERS_QUERY = """
            SELECT id, name, surname, img, login, password, last_login_date_time, profession
                    FROM users
                    WHERE login = ? AND password = ?""";
    private final Connection conn;

    public CollectionUserDAO(Connection conn) {
        this.conn = conn;
    }

    private static Scanner scanner = new Scanner(System.in);


    public List<User> getUserByLoginAndPassword1(String login, String password) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(GET_ALL_USERS_QUERY);

            statement.setString(1, login);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            // Processing the result set and populating the list of users
            while (resultSet.next()) {

                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("img"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("profession"),
                        resultSet.getTimestamp("last_login_date_time")
                );

                databaseOfUsers.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closing resources in finally block to ensure they are released properly
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return databaseOfUsers;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return databaseOfUsers.stream()
                .filter(u -> u.getId() == id)
                .findFirst();


    }

    @Override
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return databaseOfUsers.stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst();
    }


    @Override
    public boolean saveUser(User user) {
        int userIndex = databaseOfUsers.indexOf(user);
        if (userIndex != -1) {
            databaseOfUsers.set(userIndex, user);
        } else {
            databaseOfUsers.add(user);
        }
        return true;
    }

    public void loadData(List<User> users) {
        System.out.println(users.toString());
        databaseOfUsers = users;
    }

    public void writingDataToAFile(List<User> users, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadingDataFromAFile(String fileName) {
        List<User> users;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            users = (List<User>) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadData(users);
    }
}

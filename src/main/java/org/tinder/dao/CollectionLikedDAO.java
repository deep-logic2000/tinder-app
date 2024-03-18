package org.tinder.dao;

import org.tinder.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionLikedDAO {

    private static int currentUserId = 4; // Should be Auth.getCookieValue(HttpServletRequest rq)
    private static final String GET_ALL_LIKED_USERS_QUERY = """
        SELECT id_from, id_to, status, name, surname, img, last_login_date_time, profession, id
        FROM liked
        JOIN users
        on liked.id_to = users.id
        where liked.id_from = """ + currentUserId;
    private final Connection conn;
    List<User> likedUsers = new ArrayList<>();

    public CollectionLikedDAO(Connection conn) {
        this.conn = conn;
    }

    public List<User> getAllLikedUsers() {
        likedUsers = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(GET_ALL_LIKED_USERS_QUERY);

            resultSet = statement.executeQuery();

            // Processing the result set and populating the list of users
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id_to"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("img"),
                        resultSet.getString("profession"),
                        resultSet.getTimestamp("last_login_date_time")
                );

                likedUsers.add(user);

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

        return likedUsers;
    }

}

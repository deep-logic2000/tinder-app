package org.tinder.dao;

import org.tinder.Auth;
import org.tinder.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionLikedDAO {

    private static Optional<String> currentUserIdOpt;
    private static final String GET_ALL_LIKED_USERS_QUERY = """
        SELECT id_from, id_to, status, name, surname, img, last_login_date_time, profession, id
        FROM liked
        JOIN users
        on liked.id_to = users.id
        where liked.id_from = ? AND liked.status = true""";
    private final Connection conn;
    List<User> likedUsers = new ArrayList<>();

    public CollectionLikedDAO(Connection conn) {
        this.conn = conn;
    }

    public List<User> getAllLikedUsers(HttpServletRequest req) {
        likedUsers = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        currentUserIdOpt = Auth.getCookieValue(req);
        int loggedInUserId = currentUserIdOpt.map(Integer::parseInt).orElse(0);

        try {
            statement = conn.prepareStatement(GET_ALL_LIKED_USERS_QUERY);
            statement.setInt(1, loggedInUserId);

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

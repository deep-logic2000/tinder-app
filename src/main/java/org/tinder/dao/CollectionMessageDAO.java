package org.tinder.dao;

import org.tinder.Auth;
import org.tinder.Message;
import org.tinder.User;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionMessageDAO {
    private static Optional<String> currentUserIdOpt;
    private static final String GET_ALL_USERS_MESSAGES_QUERY = """
            SELECT sender_id, receiver_id, message, time, u.name, u.surname, u.img, u.id
                       FROM messages m
                JOIN users u
                on m.sender_id = u.id
                where (m.sender_id = ? AND m.receiver_id = ?) OR (m.sender_id = ? AND m.receiver_id = ?)
                order by time""";
    private final Connection conn;
    List<Message> messages = new ArrayList<>();
    User chatUser;

    public CollectionMessageDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Message> getAllUsersMessages(HttpServletRequest req) {
        messages.clear();

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        currentUserIdOpt = Auth.getCookieValue(req);
        int currentUserId = currentUserIdOpt.map(Integer::parseInt).orElse(0);

        Integer chatUserId = Integer.valueOf(getChatUserId(req));

        try {
            statement = conn.prepareStatement(GET_ALL_USERS_MESSAGES_QUERY);
            statement.setInt(1, currentUserId);
            statement.setInt(2, chatUserId);
            statement.setInt(3, chatUserId);
            statement.setInt(4, currentUserId);

            resultSet = statement.executeQuery();
            // Processing the result set and populating the list of messages
            while (resultSet.next()) {
                Boolean isMessageFromCurrUser = resultSet.getInt("sender_id") == currentUserId;

                User user = new User(
                        resultSet.getInt("sender_id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("img")
                );
                Message message = new Message(
                        resultSet.getInt("id"),
                        resultSet.getString("message"),
                        user,
                        isMessageFromCurrUser
                );

                messages.add(message);
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


        return messages;
    }

    public User getChatUser(HttpServletRequest req){
        Integer chatUserId = Integer.valueOf(getChatUserId(req));
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(
                    """
                            SELECT * from users
                            where id=?
                        """
            );
            statement.setInt(1, chatUserId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String img = resultSet.getString("img");
                chatUser = new User(id, name, surname, img);
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


        return chatUser;
    };

    String getChatUserId(HttpServletRequest req){
      String path = req.getPathInfo();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    };

}

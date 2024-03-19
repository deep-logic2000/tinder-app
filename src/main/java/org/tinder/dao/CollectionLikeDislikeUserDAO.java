package org.tinder.dao;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.tinder.Auth;
import org.tinder.Database;
import org.tinder.ResourceOps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class CollectionLikeDislikeUserDAO implements LikeDislikeUserDAO {
    private static final String GET_ALL_INFO_USERS_TO_LIKE_DISLIKE = "SELECT img, name, surname, id FROM users";
    private static final String INSERT_CHOICE_OF_USER = "INSERT INTO liked (id_from, id_to, status) VALUES(?,?,?)";
    private final Connection conn;
    private final ArrayList<String> images = new ArrayList<>();
    private final ArrayList<String> firstNames = new ArrayList<>();
    private final ArrayList<String> lastNames = new ArrayList<>();
    private final ArrayList<Integer> ids = new ArrayList<>();
    private static int currentIndex = 0;

    public CollectionLikeDislikeUserDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void getInfoAboutUserToLikeDislike(HttpServletRequest req, HttpServletResponse resp){
        try (Connection conn = Database.mkConn()) {
            String select = GET_ALL_INFO_USERS_TO_LIKE_DISLIKE;
            PreparedStatement st = conn.prepareStatement(select);
            ResultSet rs = st.executeQuery();

            Optional<String> loggedInUserIdOpt = Auth.getCookieValue(req);
            String loggedInUserId = loggedInUserIdOpt.orElse("");

            while (rs.next()) {
                int id = rs.getInt("id");
                if (!loggedInUserId.equals(String.valueOf(id))) {
                    images.add(rs.getString("img"));
                    firstNames.add(rs.getString("name"));
                    lastNames.add(rs.getString("surname"));
                    ids.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            nextUser(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void choiceLikeOrDislike(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String choiceOfUser = req.getParameter("choice");

        if (choiceOfUser != null) {
            if (choiceOfUser.equals("Like") && currentIndex < ids.size() - 1) {
                saveUserIntoDB(req, ids.get(currentIndex));
                currentIndex++;
            } else if (choiceOfUser.equals("Dislike") && currentIndex < ids.size() - 1) {
                saveUserIntoDB(req, ids.get(currentIndex));
                currentIndex++;
            } else if (currentIndex == ids.size() - 1) {
                resp.sendRedirect("/liked");
                return; // Return to stop further execution
            }
        }

        nextUser(req, resp);

    }

    @Override
    public void nextUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentImage = images.get(currentIndex);
        String currentFirstName = firstNames.get(currentIndex);
        String currentLastName = lastNames.get(currentIndex);

        HashMap<String, String> data = new HashMap<>();
        data.put("name", currentFirstName);
        data.put("surname", currentLastName);
        data.put("image", currentImage);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File(ResourceOps.resourceUnsafe("templates")));

        try (PrintWriter pw = resp.getWriter()) {
            Template template = cfg.getTemplate("like-page.ftl");
            template.process(data, pw);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveUserIntoDB(HttpServletRequest req, int id) {
        String choiceOfUser = req.getParameter("choice");

        Optional<String> userIdOpt = Auth.getCookieValue(req);
        String userId = userIdOpt.orElse("");

        if (!userId.isEmpty()) {
            boolean status = choiceOfUser.equals("Like");

            saveLikeIntoDB(Integer.parseInt(userId), id, status);
        } else {
            System.out.println("Error: userId is null.");
            System.out.println("userId: " + userIdOpt);
        }
    }

    public void saveLikeIntoDB(int userId, int likedUserId, boolean status) {
        try (Connection conn = Database.mkConn();
             PreparedStatement st = conn.prepareStatement(INSERT_CHOICE_OF_USER)) {
            st.setInt(1, userId);
            st.setInt(2, likedUserId);
            st.setBoolean(3, status);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package org.tinder.dao;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionLikeDislikeUserDAO implements LikeDislikeUserDAO{
    private static final String GET_ALL_INFO_USERS_TO_LIKE_DISLIKE = """
        SELECT img, name, surname FROM users
        """;
    private final Connection conn;
    private final ArrayList<String> images = new ArrayList<>();
    private final ArrayList<String> firstNames = new ArrayList<>();
    private final ArrayList<String> lastNames = new ArrayList<>();

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

            while (rs.next()) {
                images.add(rs.getString("img"));
                firstNames.add(rs.getString("name"));
                lastNames.add(rs.getString("surname"));
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
            if (choiceOfUser.equals("Like") && currentIndex < images.size() - 1) {
                currentIndex++;
            } else if (choiceOfUser.equals("Dislike") && currentIndex < images.size() - 1) {
                currentIndex++;
            } else if (currentIndex == images.size() - 1) {
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

        // Populate data HashMap with new values
        HashMap<String, String> data = new HashMap<>();
        data.put("name", currentFirstName);
        data.put("surname", currentLastName);
        data.put("image", currentImage);

        // Render the template with updated data
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File(ResourceOps.resourceUnsafe("templates")));

        try (PrintWriter pw = resp.getWriter()) {
            Template template = cfg.getTemplate("like-page.ftl");
            template.process(data, pw);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tinder.dao.CollectionLikedDAO;
import org.tinder.dao.LikedDAO;
import org.tinder.services.FreemarkerService;
import org.tinder.services.LikedService;
import org.tinder.servlets.*;
import javax.servlet.http.HttpServlet;
import org.tinder.servlets.LoginServlet;
import org.tinder.servlets.UsersServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class App {
    private static final String DIR_TEMPLATES_NAME = "templates";
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        Connection conn = null;

        try {
            conn = Database.mkConn();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }

        CollectionLikedDAO likedDAO = new CollectionLikedDAO(conn);

        LikedService ls = new LikedService(likedDAO);

        ServletContextHandler handler = new ServletContextHandler();
        UsersServlet usersServlet = new UsersServlet(DIR_TEMPLATES_NAME);

        FreemarkerService freemarker = new FreemarkerService(DIR_TEMPLATES_NAME);

        LoginServlet loginServlet = new LoginServlet(freemarker);

        HttpServlet likedServlet = new LikedServlet(DIR_TEMPLATES_NAME, freemarker, ls);
        HttpServlet messagesServlet = new MessagesServlet(DIR_TEMPLATES_NAME, freemarker);
        HttpServlet cssServlet = new CssServlet("templates/css");

        handler.addServlet(new ServletHolder(cssServlet), "/css/*");
        handler.addServlet(new ServletHolder(likedServlet), "/liked/*");
        handler.addServlet(new ServletHolder(messagesServlet), "/messages/*");

        handler.addServlet(new ServletHolder(usersServlet), "/users/*");

        handler.addServlet(new ServletHolder(loginServlet), "/login/*");



        server.setHandler(handler);
        server.start();
        server.join();
    }
}

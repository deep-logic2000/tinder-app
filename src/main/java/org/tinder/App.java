package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tinder.controllers.UserController;
import org.tinder.dao.CollectionUserDAO;
import org.tinder.services.FreemarkerService;
import org.tinder.services.UserService;
import org.tinder.servlets.*;
import javax.servlet.http.HttpServlet;
import org.tinder.servlets.LoginServlet;
import org.tinder.servlets.UsersServlet;

import java.sql.Connection;
import java.sql.SQLException;


public class App {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        Connection conn = null;

        try {
            conn = Database.mkConn();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }

        CollectionUserDAO collectionUserDAO = new CollectionUserDAO(conn);
        UserService userService = new UserService(collectionUserDAO);
        UserController userController = new UserController(userService);


        ServletContextHandler handler = new ServletContextHandler();
        UsersServlet usersServlet = new UsersServlet("templates");

        FreemarkerService freemarker = new FreemarkerService("templates");

        LoginServlet loginServlet = new LoginServlet(freemarker, userController);

        HttpServlet likedServlet = new LikedServlet("templates", freemarker);
        HttpServlet cssServlet = new CssServlet("templates/css");

        handler.addServlet(new ServletHolder(cssServlet), "/css/*");
        handler.addServlet(new ServletHolder(likedServlet), "/liked/*");

        handler.addServlet(new ServletHolder(usersServlet), "/users/*");

        handler.addServlet(new ServletHolder(loginServlet), "/login/*");



        server.setHandler(handler);
        server.start();
        server.join();
    }
}

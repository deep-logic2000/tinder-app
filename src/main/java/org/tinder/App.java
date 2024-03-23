package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.tinder.controllers.UserController;
import org.tinder.dao.CollectionLikeDislikeUserDAO;
import org.tinder.dao.CollectionUserDAO;
import org.tinder.dao.CollectionLikedDAO;
import org.tinder.dao.CollectionMessageDAO;
import org.tinder.filter.HttpFilter;
import org.tinder.services.FreemarkerService;
import org.tinder.services.LikeDislikeUserService;
import org.tinder.services.UserService;
import org.tinder.services.LikedService;
import org.tinder.services.MessageService;

import org.tinder.servlets.*;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumSet;

public class App {
    private static final String DIR_TEMPLATES_NAME = "templates";
    public static void main(String[] args) throws Exception {
        String portString = System.getenv("PORT") != null ? System.getenv("PORT") : "8080";
        Integer port = Integer.parseInt(portString);

        Server server = new Server(port);
        Connection conn = null;
        var sfd = EnumSet.of(DispatcherType.REQUEST);

        try {
            conn = Database.mkConn();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }

        CollectionLikedDAO likedDAO = new CollectionLikedDAO(conn);
        CollectionMessageDAO messagesDAO = new CollectionMessageDAO(conn);

        LikedService ls = new LikedService(likedDAO);
        MessageService ms = new MessageService(messagesDAO);

        CollectionUserDAO collectionUserDAO = new CollectionUserDAO(conn);
        UserService userService = new UserService(collectionUserDAO);
        UserController userController = new UserController(userService);

        FreemarkerService freemarker = new FreemarkerService(DIR_TEMPLATES_NAME);

        CollectionLikeDislikeUserDAO collectionLikeDislikeUserDAO = new CollectionLikeDislikeUserDAO(conn, freemarker);
        LikeDislikeUserService likeDislikeUserService = new LikeDislikeUserService(collectionLikeDislikeUserDAO);


        ServletContextHandler handler = new ServletContextHandler();

        UsersServlet usersServlet = new UsersServlet(likeDislikeUserService);

        LoginServlet loginServlet = new LoginServlet(freemarker, userController);
        LogoutServlet logoutServlet = new LogoutServlet(freemarker);
        HttpFilter httpFilter = new HttpFilter();

        HttpServlet likedServlet = new LikedServlet(DIR_TEMPLATES_NAME, freemarker, ls);
        HttpServlet messagesServlet = new MessagesServlet(DIR_TEMPLATES_NAME, freemarker, ms);
        HttpServlet cssServlet = new CssServlet("templates/css");
        HttpServlet imgServlet = new ImgServlet("templates/img");


        handler.addFilter(new FilterHolder(httpFilter), "/liked/*", sfd);
        handler.addFilter(new FilterHolder(httpFilter), "/messages/*", sfd);
        handler.addFilter(new FilterHolder(httpFilter), "/users/*", sfd);


        handler.addServlet(new ServletHolder(cssServlet), "/css/*");
        handler.addServlet(new ServletHolder(imgServlet), "/img/*");
        handler.addServlet(new ServletHolder(likedServlet), "/liked/*");
        handler.addServlet(new ServletHolder(messagesServlet), "/messages/*");
        handler.addServlet(new ServletHolder(usersServlet), "/users/*");
        handler.addServlet(new ServletHolder(loginServlet), "/login/*");
        handler.addServlet(new ServletHolder(logoutServlet), "/logout/*");

        handler.addServlet(RedirectServlet.class, "/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}

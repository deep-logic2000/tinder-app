package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tinder.services.FreemarkerService;
import org.tinder.servlets.*;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import org.tinder.filter.HttpFilter;
import org.tinder.services.FreemarkerService;
import org.tinder.servlets.LoginServlet;
import org.tinder.servlets.TestServlet;
import org.tinder.servlets.UsersServlet;

import javax.servlet.http.HttpServlet;


public class App {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler handler = new ServletContextHandler();
        UsersServlet usersServlet = new UsersServlet("templates");

        FreemarkerService freemarker = new FreemarkerService("templates");

        LoginServlet loginServlet = new LoginServlet(freemarker);

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

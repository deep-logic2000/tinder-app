package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tinder.services.FreemarkerService;
import org.tinder.servlets.*;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;

public class App {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler handler = new ServletContextHandler();
        UsersServlet usersServlet = new UsersServlet("templates");

        FreemarkerService freemarker = new FreemarkerService("templates");

        HttpServlet likedServlet = new LikedServlet("templates", freemarker);
        HttpServlet cssServlet = new CssServlet("templates/css");

        handler.addServlet(new ServletHolder(cssServlet), "/css/*");
        handler.addServlet(new ServletHolder(likedServlet), "/liked/*");

        handler.addServlet(TestServlet.class,"/users");
        handler.addServlet(new ServletHolder(usersServlet), "/users/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}

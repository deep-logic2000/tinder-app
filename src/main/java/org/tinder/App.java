package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
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
        FreemarkerService freemarker = new FreemarkerService("templates");
        HttpFilter filter = new HttpFilter();
        HttpServlet loginServlet = new LoginServlet(freemarker);

        handler.addServlet(new ServletHolder(loginServlet), "/login");

        handler.addServlet(TestServlet.class,"/users");

        handler.addServlet(new ServletHolder(new UsersServlet("templates")), "/users/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}

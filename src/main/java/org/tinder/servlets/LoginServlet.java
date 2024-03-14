package org.tinder.servlets;

import org.tinder.Auth;
import org.tinder.User;
import org.tinder.controllers.UserController;
import org.tinder.services.FreemarkerService;
import org.tinder.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private final FreemarkerService freemarker;

    public LoginServlet(FreemarkerService freemarker) {
        this.freemarker = freemarker;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message1 = Auth.getCookieValue(req)
                .map(id -> String.format("User  %s was logged out", id))
                .orElse("Nobody was logged in");

        String id = UUID.randomUUID().toString();
        String message2 = String.format("User %s logged in", id);

        HashMap<String, Object> data = new HashMap<>();
        data.put("message1", message1);
        data.put("message2", message2);

        try (PrintWriter w = resp.getWriter()) {
            Auth.setCookieValue(id, resp);
            freemarker.render("login.ftl", data, w);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userController.getUserByLoginAndPassword(login, password);

        String message1 = Auth.getCookieValue(req)
                .map(id -> String.format("User  %s was logged out", id))
                .orElse("Nobody was logged in");


        String message2 = String.format("User %s logged in", String.valueOf(user.getId()));

        HashMap<String, Object> data = new HashMap<>();
        data.put("message1", message1);
        data.put("message2", message2);

        if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
            Auth.setCookieValue(String.valueOf(user.getId()), resp);

            resp.sendRedirect("like-page.ftl");

        } else {

            resp.sendRedirect("login.ftl");

        }

        try (PrintWriter w = resp.getWriter()) {
            Auth.setCookieValue(String.valueOf(user.getId()), resp);
            freemarker.render("login.ftl", data, w);
        }

    }
}

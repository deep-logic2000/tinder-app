package org.tinder.servlets;

import org.tinder.Auth;
import org.tinder.User;
import org.tinder.controllers.UserController;
import org.tinder.services.FreemarkerService;
import org.tinder.services.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


public class LoginServlet extends HttpServlet {
    private final FreemarkerService freemarker;

    private boolean isLoginValid = true;

    public LoginServlet(FreemarkerService freemarker) {
        this.freemarker = freemarker;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> usersForRender = new HashMap<>();
        String massage = isLoginValid ? "" : "Incorrect login or password";
        usersForRender.put("errorMassage", massage);

        try (PrintWriter w = resp.getWriter()) {
            freemarker.render("login.ftl", usersForRender, w);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        ArrayList<User> users = getUsers();

        userController.loadData(users);


        Optional<User> user = userController.getUserByLoginAndPassword(login, password);

        if (user.isEmpty()) {
            isLoginValid = false;
            resp.sendRedirect("/login");

        } else if (login.equals(user.get().getLogin()) && password.equals(user.get().getPassword())) {
            Auth.setCookieValue(String.valueOf(user.get().getId()), resp);

            resp.sendRedirect("/users");

        }

    }

    private static ArrayList<User> getUsers() {
        return UsersServlet.users;

    }
}

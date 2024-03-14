package org.tinder.servlets;

import org.tinder.Auth;
import org.tinder.services.FreemarkerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class LogoutServlet extends HttpServlet {
    private final FreemarkerService freemarker;

    public LogoutServlet(FreemarkerService freemarker) {
        this.freemarker = freemarker;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = Auth.getCookieValue(req)
                .map(id -> String.format("User  %s was logged out", id))
                .orElse("Nobody was logged in");

        HashMap<String, Object> data = new HashMap<>();
        data.put("message", message);

        try (PrintWriter w = resp.getWriter()) {
            Auth.clearCookie(resp);
            freemarker.render("calc-logout.ftl", data, w);
        }
    }
}

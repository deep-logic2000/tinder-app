package org.tinder.servlets;

import org.tinder.User;
import org.tinder.services.FreemarkerService;
import org.tinder.services.LikedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LikedServlet extends HttpServlet {

    private final String root;
    private final FreemarkerService freemarker;
    private LikedService ls;

    public LikedServlet(String fileName, FreemarkerService freemarker, LikedService ls) {
        this.root = fileName;
        this.freemarker = freemarker;
        this.ls = ls;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> usersFromDb = ls.getAllLikedUsers();

        HashMap<String, Object> usersForRender = new HashMap<>();
        usersForRender.put("users", usersFromDb);

        try (PrintWriter w = resp.getWriter()) {
            freemarker.render("people-list.ftl", usersForRender, w);
        }
    }
}

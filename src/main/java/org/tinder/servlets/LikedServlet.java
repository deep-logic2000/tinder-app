package org.tinder.servlets;

import org.tinder.ResourceOps;
import org.tinder.User;
import org.tinder.dao.CollectionLikedDAO;
import org.tinder.services.FreemarkerService;
import org.tinder.services.LikedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        ArrayList<User> users = getUsers();
        List<User> usersFromDb = ls.getAllLikedUsers();
        usersFromDb.stream().forEach(user -> System.out.println(user.toString()));

        HashMap<String, Object> usersForRender = new HashMap<>();
        usersForRender.put("users", usersFromDb);


        try (PrintWriter w = resp.getWriter()) {
            freemarker.render("people-list.ftl", usersForRender, w);
//            freemarker.render("people-list.ftl", usersForRender, w);
        }
    }

    private static ArrayList<User> getUsers() {
        return UsersServlet.users;
    }

}

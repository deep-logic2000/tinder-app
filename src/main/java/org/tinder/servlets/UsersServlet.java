package org.tinder.servlets;

import org.tinder.User;
import org.tinder.services.LikeDislikeUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class UsersServlet extends HttpServlet {
    private final String fileName;
    private LikeDislikeUserService likeDislikeUserService;

    public static ArrayList<User> users = new ArrayList<>();

    public UsersServlet(String fileName, LikeDislikeUserService likeDislikeUserService) {
        this.fileName = fileName;
        this.likeDislikeUserService = likeDislikeUserService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        likeDislikeUserService.getInfoAboutUserToLikeDislike(request, response);

        likeDislikeUserService.nextUser(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        likeDislikeUserService.choiceLikeOrDislike(req, resp);

    }

}

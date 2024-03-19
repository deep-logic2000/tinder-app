package org.tinder.servlets;

import org.tinder.User;
import org.tinder.controllers.LikeDislikeUserController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class UsersServlet extends HttpServlet {
    private final String fileName;
    private final LikeDislikeUserController likeDislikeUserController;

    public static ArrayList<User> users = new ArrayList<>();

    public UsersServlet(String fileName, LikeDislikeUserController likeDislikeUserController) {
        this.fileName = fileName;
        this.likeDislikeUserController = likeDislikeUserController;

        User user1 = new User(0, "John", "Doe", "https://img.freepik.com/free-photo/inside-portrait-of-confident-young-man-in-white-clothes-posing-with-charming-smile-over-isolated-wall_291650-95.jpg?w=740&t=st=1710269612~exp=1710270212~hmac=21ec49050565c93e301030c28f4daed2d5b08e0c86153223d01ecf85923025b1", "1", "1", "Sales manager", new Date(2020, 01, 01));
        User user2 = new User(1, "Jack", "Brown", "https://img.freepik.com/free-photo/handsome-bearded-guy-posing-against-the-white-wall_273609-20597.jpg?w=740&t=st=1710337219~exp=1710337819~hmac=5cc2f97c6f732663872cb5faa6f6e015f72f23e5a32be7588a4e4d2be38c044b", "2", "2", "Director", new Date(2021, 01, 01));
        User user3 = new User(2, "Richard", "Black", "https://img.freepik.com/free-photo/confident-handsome-guy-posing-against-the-white-wall_176420-32936.jpg?w=740&t=st=1710269788~exp=1710270388~hmac=ef7a6655b42d094415b322195b05435439e7089a9f9f64c465015f28e4b20721", "3", "3", "Football player", new Date(2022, 01, 01));

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        likeDislikeUserController.getSomething(request, response);

        likeDislikeUserController.nextUser(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        likeDislikeUserController.choiceLikeOrDislike(req, resp);

    }

}

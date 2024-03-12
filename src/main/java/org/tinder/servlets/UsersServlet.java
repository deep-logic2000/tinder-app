package org.tinder.servlets;

import org.tinder.ResourceOps;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UsersServlet extends HttpServlet {

    private final String fileName;

    public UsersServlet(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String prefix = ResourceOps.resourceUnsafe(fileName);
//        System.out.println(prefix);

        String fileInfo = request.getPathInfo();
//        System.out.println(fileInfo);

        String fullName = prefix + fileInfo;
//        System.out.println(fullName);

        if(!new File(fullName).exists()) {
            response.setStatus(404);
        } else try (ServletOutputStream os = response.getOutputStream()) {
            Path path = Paths.get(fullName);
            Files.copy(path, os);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String choiceOfUser1 = req.getParameter("Like");
        String choiceOfUser2 = req.getParameter("Dislike");

        if ((choiceOfUser1 != null && choiceOfUser1.equals("Like")) || (choiceOfUser2 != null && choiceOfUser2.equals("Dislike"))) {
            if (choiceOfUser1 != null && choiceOfUser1.equals("Like")) {
                System.out.println("User chose Like");
            } else if (choiceOfUser2 != null && choiceOfUser2.equals("Dislike")) {
                System.out.println("User chose Dislike");
            } else {
                System.out.println("Something unexpected");
            }
        }

        resp.sendRedirect("/users/like-page.html");
    }

}

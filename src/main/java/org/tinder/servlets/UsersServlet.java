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
        System.out.println(prefix);

        String fileInfo = request.getPathInfo();
        System.out.println(fileInfo);

        String fullName = prefix + fileInfo;
        System.out.println(fullName);

        if(!new File(fullName).exists()) {
            response.setStatus(404);
        } else try (ServletOutputStream os = response.getOutputStream()) {
            Path path = Paths.get(fullName);
            Files.copy(path, os);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String choiceOfUser = req.getParameter("Yes/No");

        if (choiceOfUser != null && choiceOfUser.equals("yes") || choiceOfUser.equals("no")){
            if (choiceOfUser.equals("yes")){
                System.out.printf("User chose %s", choiceOfUser);
            } else if (choiceOfUser.equals("no")){
                System.out.printf("User chose %s", choiceOfUser);
            } else {
                System.out.println("Something unexpected");
            }
        }

        resp.sendRedirect("/users/like-page.html");
    }
}

package org.tinder.servlets;

import org.tinder.ResourceOps;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CssServlet extends HttpServlet {
    private final String root;

    public CssServlet(String root) {
        this.root = root;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String prefix = ResourceOps.resourceUnsafe(root);
        String fileName = req.getPathInfo();
        String fullName = prefix + fileName;

        if (!new File(fullName).exists()) {
            resp.setStatus(404);
        } else try (ServletOutputStream os = resp.getOutputStream()) {
            Path path = Paths.get(fullName);
            Files.copy(path, os);
        }
    }
}

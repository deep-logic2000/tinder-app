package org.tinder.servlets;

import org.tinder.ResourceOps;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class CssServlet extends HttpServlet {
    private final String root;

    public CssServlet(String root) {
        this.root = root;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rqName = "/templates" + req.getRequestURI();
        ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println("rqName: " + rqName);

        try (ServletOutputStream os = resp.getOutputStream();
           InputStream grs = classLoader.getResourceAsStream(rqName.substring(1))) {

            byte[] bytes = grs.readAllBytes();
            System.out.println(Arrays.toString(bytes));

            os.write(bytes);

        } catch (NullPointerException | IOException ex) {
            resp.setStatus(404);
        }
    }
}

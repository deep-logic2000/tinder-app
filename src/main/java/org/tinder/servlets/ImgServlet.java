package org.tinder.servlets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ImgServlet extends HttpServlet {
    private final String root;

    public ImgServlet(String root) {
        this.root = root;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rqName = "/templates" + req.getRequestURI();
        ClassLoader classLoader = this.getClass().getClassLoader();

        try (ServletOutputStream os = resp.getOutputStream();
             InputStream grs = classLoader.getResourceAsStream(rqName.substring(1))) {

            byte[] bytes = grs.readAllBytes();

            os.write(bytes);
        } catch (NullPointerException | IOException ex) {
            resp.setStatus(404);
        }
    }
}

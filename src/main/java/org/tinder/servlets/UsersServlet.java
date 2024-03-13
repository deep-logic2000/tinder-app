package org.tinder.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.tinder.ResourceOps;
import org.tinder.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UsersServlet extends HttpServlet {
    private final String fileName;
    public static ArrayList<User> users = new ArrayList<>();

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> firstNames = new ArrayList<>();
    private ArrayList<String> lastNames = new ArrayList<>();
    private int currentIndex = 0;

    public UsersServlet(String fileName) {
        this.fileName = fileName;
        User user1 = new User(0, "John", "Doe", "https://img.freepik.com/free-photo/inside-portrait-of-confident-young-man-in-white-clothes-posing-with-charming-smile-over-isolated-wall_291650-95.jpg?w=740&t=st=1710269612~exp=1710270212~hmac=21ec49050565c93e301030c28f4daed2d5b08e0c86153223d01ecf85923025b1", "1", "1", "Sales manager", new Date(2020, 01, 01));
        User user2 = new User(1, "Jack", "Brown", "https://img.freepik.com/free-photo/handsome-bearded-guy-posing-against-the-white-wall_273609-20597.jpg?w=740&t=st=1710337219~exp=1710337819~hmac=5cc2f97c6f732663872cb5faa6f6e015f72f23e5a32be7588a4e4d2be38c044b", "2", "2", "Director", new Date(2021, 01, 01));
        User user3 = new User(2, "Richard", "Black", "https://img.freepik.com/free-photo/confident-handsome-guy-posing-against-the-white-wall_176420-32936.jpg?w=740&t=st=1710269788~exp=1710270388~hmac=ef7a6655b42d094415b322195b05435439e7089a9f9f64c465015f28e4b20721", "3", "3", "Football player", new Date(2022, 01, 01));

        users.add(user1);
        users.add(user2);
        users.add(user3);

        images.add("https://images.pexels.com/photos/18083418/pexels-photo-18083418.jpeg?auto=compress&cs=tinysrgb&w=600");
        images.add("https://images.pexels.com/photos/16162647/pexels-photo-16162647.jpeg?auto=compress&cs=tinysrgb&w=600");
        images.add("https://images.pexels.com/photos/775358/pexels-photo-775358.jpeg?auto=compress&cs=tinysrgb&w=600");

        // Додайте інші імена та прізвища, якщо потрібно
        firstNames.add("Olena");
        firstNames.add("Iruna");
        firstNames.add("Ostap");

        lastNames.add("Melnyk");
        lastNames.add("Kozak");
        lastNames.add("Luba");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File(
                ResourceOps.resourceUnsafe("templates")
        ));

        String currentImage = images.get(currentIndex);
        String currentFirstName = firstNames.get(currentIndex);
        String currentLastName = lastNames.get(currentIndex);

        HashMap<String, String> data = new HashMap<>();
        data.put("name", currentFirstName);
        data.put("surname", currentLastName);
        data.put("image", currentImage);

        try (PrintWriter pw = response.getWriter()) {
            Template template = cfg.getTemplate("like-page.ftl");
            template.process(data, pw);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choiceOfUser = req.getParameter("choice");

        if (choiceOfUser != null) {
            if (choiceOfUser.equals("Like")) {
                currentIndex = (currentIndex + 1) % images.size(); // Збільшуємо індекс зображення на 1, обмежуємо його до розміру списку
            } else if (choiceOfUser.equals("Dislike")) {
                currentIndex = (currentIndex + 1) % images.size();
            }
        }

        resp.sendRedirect("/users");
    }
}



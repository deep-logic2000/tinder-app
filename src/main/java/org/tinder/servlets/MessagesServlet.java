package org.tinder.servlets;

import org.tinder.Message;
import org.tinder.User;
import org.tinder.services.FreemarkerService;
import org.tinder.services.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class MessagesServlet extends HttpServlet {

    private final String root;
    private final FreemarkerService freemarker;
    private MessageService ms;

    public MessagesServlet(String fileName, FreemarkerService freemarker, MessageService ms) {
        this.root = fileName;
        this.freemarker = freemarker;
        this.ms = ms;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Message> messagesFromDB = ms.getAllUsersMessages(req);

        User chatUser = ms.getChatUser(req);

        HashMap<String, Object> messagesForRender = new HashMap<>();
        messagesForRender.put("senderName", chatUser.getName());
        messagesForRender.put("senderSurname", chatUser.getSurname());
        messagesForRender.put("senderPhoto", chatUser.getImg());
        messagesForRender.put("messages", messagesFromDB);


        try (PrintWriter w = resp.getWriter()) {
            freemarker.render("chat.ftl", messagesForRender, w);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean rs = ms.sendMessage(req, resp);
        String path = req.getPathInfo();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if(rs) {
            resp.sendRedirect("/messages/" + path);
        } else {
            resp.sendRedirect("/liked");
        }
    }
}

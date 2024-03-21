package org.tinder.services;

import org.tinder.Message;
import org.tinder.User;
import org.tinder.dao.CollectionMessageDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MessageService {

    private CollectionMessageDAO md;

    public MessageService(CollectionMessageDAO md) {
        this.md = md;
    }

    public List<Message> getAllUsersMessages(HttpServletRequest req){
        return md.getAllUsersMessages(req);
    }

    public User getChatUser(HttpServletRequest req){
        return md.getChatUser(req);
    }

    public boolean sendMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return md.sendMessage(req, resp);
    }

}

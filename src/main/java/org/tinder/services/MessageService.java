package org.tinder.services;

import org.tinder.Message;
import org.tinder.User;
import org.tinder.dao.CollectionMessageDAO;

import java.util.List;

public class MessageService {

    private CollectionMessageDAO md;

    public MessageService(CollectionMessageDAO md) {
        this.md = md;
    }

    public List<Message> getAllUsersMessages(){
        return md.getAllUsersMessages();
    }
}

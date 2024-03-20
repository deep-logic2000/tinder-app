package org.tinder.services;

import org.tinder.User;
import org.tinder.dao.CollectionLikedDAO;
import org.tinder.dao.LikedDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LikedService {

    private CollectionLikedDAO ld;

    public LikedService(CollectionLikedDAO ld) {
        this.ld = ld;
    }

    public List<User> getAllLikedUsers(HttpServletRequest req){
        return ld.getAllLikedUsers(req);
    }

}

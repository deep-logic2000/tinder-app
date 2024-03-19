package org.tinder.services;

import org.tinder.dao.CollectionLikeDislikeUserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LikeDislikeUserService {
    private CollectionLikeDislikeUserDAO likeDislikeUserDAO;

    public LikeDislikeUserService(CollectionLikeDislikeUserDAO likeDislikeUserDAO) {
        this.likeDislikeUserDAO = likeDislikeUserDAO;
    }

    public void getSomething(HttpServletRequest req, HttpServletResponse resp){
        likeDislikeUserDAO.getInfoAboutUserToLikeDislike(req, resp);
    }

    public void nextUser(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        likeDislikeUserDAO.nextUser(req, resp);
    }

    public void choiceLikeOrDislike(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        likeDislikeUserDAO.choiceLikeOrDislike(req, resp);
    }
}

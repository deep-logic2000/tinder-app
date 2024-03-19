package org.tinder.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LikeDislikeUserDAO {
    void getInfoAboutUserToLikeDislike(HttpServletRequest req, HttpServletResponse resp);
    void choiceLikeOrDislike(HttpServletRequest req, HttpServletResponse resp) throws IOException;
    void nextUser(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}

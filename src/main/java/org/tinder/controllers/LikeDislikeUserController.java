package org.tinder.controllers;

import org.tinder.services.LikeDislikeUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LikeDislikeUserController {
    private LikeDislikeUserService likeDislikeUserService;

    public LikeDislikeUserController(LikeDislikeUserService likeDislikeUserService) {
        this.likeDislikeUserService = likeDislikeUserService;
    }

    public void getSomething(HttpServletRequest req, HttpServletResponse resp){
        likeDislikeUserService.getSomething(req, resp);
    }

    public void nextUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        likeDislikeUserService.nextUser(req, resp);
    }

    public void choiceLikeOrDislike(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        likeDislikeUserService.choiceLikeOrDislike(req, resp);
    }
}

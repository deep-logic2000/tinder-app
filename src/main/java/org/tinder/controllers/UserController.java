package org.tinder.controllers;

import org.tinder.User;
import org.tinder.services.UserService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public class UserController implements Serializable {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public List<User> getUserByLoginAndPasswordByDB(String login, String password) {
        return userService.getUserByLoginAndPasswordByDB(login, password);
    }

    public Optional<User> getUserFromId(long id) {
        return userService.getUserFromId(id);

    }
    public Optional<User> getUserByLoginAndPasswordByDAO(String login, String password) {
        return userService.getUserByLoginAndPasswordByDAO(login, password);
    }


    public boolean saveUser(User user) {

        return userService.saveUser(user);
    }

}

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

    public Optional<User> getUserFromId(long id) {
        return userService.getUserFromId(id);

    }

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return userService.getUserByLoginAndPassword(login, password);
    }


    public boolean saveUser(User user) {

        return userService.saveUser(user);
    }

    public void loadData(List<User> users) {
        userService.loadData(users);
    }

    public void writingDataToAFile(List<User> users, String fileName) {
        userService.writingDataToAFile(users, fileName);
    }

    public void loadingDataFromAFile(String fileName) {
        userService.loadingDataFromAFile(fileName);
    }
}

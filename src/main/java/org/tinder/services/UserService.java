package org.tinder.services;

import org.tinder.User;
import org.tinder.dao.CollectionUserDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public class UserService implements Serializable {
    private CollectionUserDAO usersDataBase;

    public UserService(CollectionUserDAO usersDataBase) {
        this.usersDataBase = usersDataBase;
    }


    public List<User> getUserByLoginAndPassword1(String login, String password) {
        return usersDataBase.getUserByLoginAndPassword1(login, password);
    }

    public Optional<User> getUserFromId(long id) {
        return usersDataBase.getUserById(id);
    }

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return usersDataBase.getUserByLoginAndPassword(login, password);
    }

    public boolean saveUser(User user) {

        return usersDataBase.saveUser(user);
    }

    public void loadData(List<User> users) {
        usersDataBase.loadData(users);
    }

    public void writingDataToAFile(List<User> users, String fileName) {
        usersDataBase.writingDataToAFile(users, fileName);
    }

    public void loadingDataFromAFile(String fileName) {
        usersDataBase.loadingDataFromAFile(fileName);
    }
}

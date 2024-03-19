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


    public List<User> getUserByLoginAndPasswordByDB(String login, String password) {
       return usersDataBase.getUserByLoginAndPasswordByDB(login, password);
    }

    public Optional<User> getUserFromId(long id) {
        return usersDataBase.getUserById(id);
    }

    public Optional<User> getUserByLoginAndPasswordByDAO(String login, String password) {
        return usersDataBase.getUserByLoginAndPasswordByDAO(login, password);
    }

    public boolean saveUser(User user) {

        return usersDataBase.saveUser(user);
    }

}

package org.tinder.services;

import org.tinder.User;
import org.tinder.dao.CollectionUserDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public class UserService implements Serializable {
    private CollectionUserDAO usersDataBase = new CollectionUserDAO();


    public User getUserFromId(long id) {
        return usersDataBase.getUserById(id);

    }

    public User getUserByLoginAndPassword(String login, String password) {
        return usersDataBase.getUserByLoginAndPassword(login, password);
    }

    public void createUser() {
        usersDataBase.createUser();
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

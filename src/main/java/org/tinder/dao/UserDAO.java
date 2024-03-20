package org.tinder.dao;

import org.tinder.User;

import java.util.Optional;


public interface UserDAO {
    Optional<User> getUserById(long id);
    Optional<User> getUserByLoginAndPasswordByDAO(String login, String password);
boolean saveUser(User user);



}

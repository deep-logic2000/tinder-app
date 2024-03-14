package org.tinder.dao;

import org.tinder.User;



public interface UserDAO {
User getUserById(long id);
User getUserByLoginAndPassword(String login, String password);
void createUser();
boolean saveUser(User user);



}

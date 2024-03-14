package org.tinder.dao;

import org.tinder.User;

import java.io.*;
import java.util.*;

public class CollectionUserDAO implements UserDAO {

    private List<User> databaseOfUsers = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    @Override
    public User getUserById(long id) {
        Optional<User> optionalUser = databaseOfUsers.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        return optionalUser.get();

    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        Optional<User> optionalUser = databaseOfUsers.stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst();

        return optionalUser.get();
    }

    @Override
    public void createUser() {
        System.out.println("Input Name:");
        long id = scanner.nextInt();
        System.out.println("Input Name:");
        String name = scanner.nextLine();
        System.out.println("Input Surname:");
        String surname = scanner.nextLine();
        System.out.println("Input imagine link:");
        String imgLink = scanner.nextLine();
        System.out.println("Input login:");
        String login = scanner.nextLine();
        System.out.println("Input password:");
        String password = scanner.nextLine();

        User user = new User(id, name, surname, imgLink, login, password);

        databaseOfUsers.add(user);
    }

    @Override
    public boolean saveUser(User user) {
        int userIndex = databaseOfUsers.indexOf(user);
        if (userIndex != -1) {
            databaseOfUsers.set(userIndex, user);
        } else {
            databaseOfUsers.add(user);
        }
        return true;
    }

    public void loadData(List<User> users) {
        databaseOfUsers = users;
    }

    public void writingDataToAFile(List<User> users, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadingDataFromAFile(String fileName) {
        List<User> users;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            users = (List<User>) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadData(users);
    }
}

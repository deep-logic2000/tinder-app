package org.tinder;

public class User {
    private int id;
    private String name;
    private String surname;
    private String img;
    private String login;
    private String password;

    public User(int id, String name, String surname, String img, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.img = img;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", img='" + img + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getImg() {
        return img;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

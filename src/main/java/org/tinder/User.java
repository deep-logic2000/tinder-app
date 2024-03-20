package org.tinder;
import java.util.Date;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String surname;
    private String img;
    private String login;
    private String password;
    private String profession;
    private Date lastDateLogin;
    private String lastDateLoginString;

    public User(int id, String name, String surname, String img, String login, String password, String profession, Date dateLogin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.img = img;
        this.login = login;
        this.password = password;
        this.profession = profession;
        this.lastDateLogin = dateLogin;
        this.lastDateLoginString = getLastDateLoginString();
    }

    public User(int id, String name, String surname, String img, String profession, Date dateLogin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.img = img;
        this.profession = profession;
        this.lastDateLogin = dateLogin;
        this.lastDateLoginString = getLastDateLoginString();
    }

    public User(int id, String name, String surname, String img) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.img = img;
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


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Date getLastDateLogin() {
        return lastDateLogin;
    }

    public void setLastDateLogin(Date lastDateLogin) {
        this.lastDateLogin = lastDateLogin;
    }

    public String getLastDateLoginString() {
        int day = lastDateLogin.getDay();
        int month = lastDateLogin.getMonth() + 1;
        int year = lastDateLogin.getYear();
        String dateToShow = String.format("%02d/%02d/%d", day, month, year);
        return dateToShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(img, user.img) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(profession, user.profession) && Objects.equals(lastDateLogin, user.lastDateLogin) && Objects.equals(lastDateLoginString, user.lastDateLoginString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, img, login, password, profession, lastDateLogin, lastDateLoginString);
    }
}

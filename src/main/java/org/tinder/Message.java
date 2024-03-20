package org.tinder;

public class Message {
    private int id;
    private String text;
    private User sender;
    Boolean isFromCurrentUser;

    public Message(int id, String text, User user, Boolean isFromCurrentUser) {
        this.id = id;
        this.text = text;
        this.sender = user;
        this.isFromCurrentUser = isFromCurrentUser;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getSender() {
        return sender;
    }

    public Boolean getIsFromCurrentUser() {
        return isFromCurrentUser;
    }

    public void setIsFromCurrentUser(Boolean fromCurrentUser) {
        isFromCurrentUser = fromCurrentUser;
    }
}

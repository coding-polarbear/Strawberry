package com.example.urjiwon.strawberry.models;

/**
 * Created by baehy on 2017-02-17.
 */

public class User {
    private String userID = null;
    private String userPassword = null;
    private String userName;
    public User() {}
    public String getUserID() {
        return userID;
    }
    public User(String userID, String userPassword, String userName) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
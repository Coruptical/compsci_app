package com.mpopoaei.compsci_app.client.models;

import java.util.ArrayList;

public class User {
    private String userID;
    private String username;
    private String password;
    private String passwordSalt;

    public User(String userID, String username, String password, String passwordSalt) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.passwordSalt = passwordSalt;
    }
}

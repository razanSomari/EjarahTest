package com.example.ruzun.ejarahtest;

public class User {
    private String email;
    private String name;
    private String UserID;

    public User(){

    }
    public User(String email, String name){
        this.email = email;
        this.name = name;


    }


    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

}


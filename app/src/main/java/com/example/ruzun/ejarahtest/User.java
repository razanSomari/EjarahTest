package com.example.ruzun.ejarahtest;

public class User {
    private String email;
    private String name;
    private String UserID;
    private Level level;
    private int points;

    public User(){

    }
    public User(String email, String name){
        this.email = email;
        this.name = name;
        points = 0;


    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

}


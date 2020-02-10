package com.example.ruzun.ejarahtest;

public class Post {
    private int points;
    private String username;
    private String content;

    Post (String username, String content, int points){
        this.content=content;
        this.points=points;
        this.username=username;
    }

    public int getPoints() {
        return points;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

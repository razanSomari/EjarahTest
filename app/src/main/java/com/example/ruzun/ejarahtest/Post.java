package com.example.ruzun.ejarahtest;

public class Post {
    private int points;
    private String username;
    private String content;
    private String postID;
    private String name;
    Post(){

    }
    Post (String username, String content, int points){
        this.content=content;
        this.points=points;
        this.username=username;
    }
    Post (String username, String name, String content){
        this.content=content;
        this.name = name;
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

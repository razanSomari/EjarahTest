package com.example.ruzun.ejarahtest;

import android.media.Image;

import com.firebase.geofire.GeoLocation;

import java.util.ArrayList;

public class Post {
    private int points;
    private String username;
    private String content;
    private String postID;
    private String name;
    private String tag;
    private String userid;
    private String catogry;
    private Image pic;
    private ArrayList<Double> location;



    Post(){

    }
    public Post (String username, String content, int points){
        this.content=content;
        this.points=points;
        this.username=username;
    }
    public Post( String username, String content, String name ) {

        this.username=username;
        this.content=content;
        this.name=name;

    }
    public Post( String username, String content, String tag,String name, ArrayList<Double> location, String catogry ) {

        this.username=username;
        this.content=content;
        this.tag=tag;
        this.name=name;
        this.location = location;
        this.catogry = catogry;
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

    public String getTag() {
        return tag;
    }

    public String getUserid() {
        return userid;
    }

    public Image getPic() {
        return pic;
    }

    public String getPostID() {
        return postID;
    }

    public String getName() {
        return name;
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

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPic(Image pic) {
        this.pic = pic;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<Double> location) {
        this.location = location;
    }

    public void setCatogry(String catogry) {
        this.catogry = catogry;
    }

    public String getCatogry() {
        return catogry;
    }
}


package com.example.ruzun.ejarahtest;

public class Level {

    private int points;
    private String category;
    private String ID;
    Level(){

    }

    Level(int points, String category){
        this.points = points;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
}

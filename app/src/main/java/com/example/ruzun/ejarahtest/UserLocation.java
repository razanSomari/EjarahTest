package com.example.ruzun.ejarahtest;

import com.firebase.geofire.GeoLocation;

import java.util.ArrayList;

public class UserLocation {
    private String g;
    private ArrayList<Double> l;

    UserLocation(){

    }

    UserLocation(String g,ArrayList<Double> l ){
        this.g = g;
        this.l = l;
    }

    public ArrayList<Double> getL() {
        return l;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public void setL(ArrayList<Double> l) {
        this.l = l;
    }
}

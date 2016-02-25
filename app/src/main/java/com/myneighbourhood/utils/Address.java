package com.myneighbourhood.utils;

/**
 * Created by kirchoni on 25/02/16.
 */
public class Address {

    private int userId;
    private String address;
    private double rectX;
    private double rextY;

    public Address(int userId, String address, double rectX, double rectY){
        this.userId = userId;
        this.address = address;
        this.rectX = rectX;
        this.rextY = rectY;
    }

    public Address(String address, double rectX, double rectY){
        this.address = address;
        this.rectX = rectX;
        this.rextY = rectY;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRectX() {
        return rectX;
    }

    public void setRectX(double rectX) {
        this.rectX = rectX;
    }

    public double getRextY() {
        return rextY;
    }

    public void setRextY(double rextY) {
        this.rextY = rextY;
    }
}

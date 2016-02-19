package com.myneighbourhood.utils;

/**
 * Created by kirchoni on 19/02/16.
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String image;
    private String phone;
    private String email;


    public User(String username, String password, String phone, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(int id, String username, String password, String phone, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

package com.example.chatiniapp.Models;

public class User {


    private int id;

    private String username, fullname, email , phone, place, img;

    private boolean isActive;

    public User() {
    }

    public User(int  id, String username, String img) {
        this.id=id;
        this.username = username;
        this.img = img;
    }

    public User(int id, String username, String fullname, String email, String phone, String place, String img, boolean isActive) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.place = place;
        this.img = img;
        this.isActive = isActive;
    }


    public long getId() {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

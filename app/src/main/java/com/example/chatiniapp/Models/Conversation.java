package com.example.chatiniapp.Models;

public class Conversation {
    private int id;
    private String senderName, senderImge, msg, date;


    public Conversation(int id, String senderName, String senderImge, String msg, String date) {
        this.id = id;
        this.senderName = senderName;
        this.senderImge = senderImge;
        this.msg = msg;
        this.date = date;
    }

    public String getName() {
        return senderName;
    }

    public void setName(String name) {
        this.senderName = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg() {
        return senderImge;
    }

    public void setImg(String img) {
        this.senderImge = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conversation() {

    }
}

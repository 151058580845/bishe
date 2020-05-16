package com.example.liaotian.entity;

public class Tousu {
    private int id;
    private String date;
    private String message;
    private int is_chuli;

    public int getIs_chuli() {
        return is_chuli;
    }

    public void setIs_chuli(int is_chuli) {
        this.is_chuli = is_chuli;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.example.slvi;

public class Inquiry {
    String name, email, message, reply;

    public Inquiry(){}

    public Inquiry(String name, String email, String message, String reply) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.reply = reply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}

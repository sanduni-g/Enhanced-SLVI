package com.example.slvi;

public class UserFeedback {
    String experience, message;

    public UserFeedback() {
    }

    public UserFeedback(String experience, String message) {
        this.experience = experience;
        this.message = message;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

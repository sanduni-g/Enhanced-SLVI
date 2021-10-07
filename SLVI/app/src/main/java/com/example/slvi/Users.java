package com.example.slvi;

public class Users {
    String name, email, creationDate;

    public Users(){
    }

    public Users(String name, String email, String creationDate) {
        this.name = name;
        this.email = email;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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
}

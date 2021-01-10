package com.example.Models;

public class User {
    public String name;
    public String phone;
    public String email;
    public String password;
    public String avatar;
    public String bio;
    public String token;

    public User(String name, String bio, String email, String avatar) {
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.avatar = avatar;
    }

    public User(String name, String phone, String email, String password, String avatar, String bio, String token) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.bio = bio;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBio() {
        return bio;
    }

    public String getToken() {
        return token;
    }
}

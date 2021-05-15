package com.FitCom.fitcomapplication.Registery;

public class User {
    public String email, age, trainer;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String age, String trainer) {
        this.email = email;
        this.age = age;
        this.trainer = trainer;
    }
}

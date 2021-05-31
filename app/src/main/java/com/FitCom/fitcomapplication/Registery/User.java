package com.FitCom.fitcomapplication.Registery;

public class User {
    public String email, age, trainer, fullName;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String age, String trainer, String fullName) {
        this.email = email;
        this.age = age;
        this.trainer = trainer;
        this.fullName = fullName;
    }
}

package com.example.hiapp;

import android.app.Application;

import com.parse.Parse;

public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("oLNjUA47yKgheahwT9S5aAnLCm6yMmYHt8KjJvAk")
                .clientKey("brrWZAX2ezPJr15gfZsd1xRw2vG1URYR9h8Bw5UU")
                .server("https://parseapi.back4app.com/")
                .build());
    }
}

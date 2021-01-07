package com.example.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Base6Dl0Zi81QsAYSRyohD7wPmF8JqI4q3elj26v")
                // if defined
                .clientKey("fyz13dCjYCR4cjpqI33RtSAdvt6KRZyYd84lOHD1")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
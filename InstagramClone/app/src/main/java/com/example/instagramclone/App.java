package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("HrBQeJJfLmF7C7j24JXRIGnzy8E8YUjneh9176Sx")
                // if defined
                .clientKey("qLzfpAfAjYoz7bIPAnldLHjzX6CgHRj1tXDcq9AC")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

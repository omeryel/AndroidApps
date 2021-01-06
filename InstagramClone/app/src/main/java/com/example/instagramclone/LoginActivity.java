package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_username,edt_password;
    private  Button btn_signup,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Log In");
        setContentView(R.layout.activity_login);

        edt_password = findViewById(R.id.txt_password_login);
        edt_username = findViewById(R.id.txt_username_login);
        btn_login  = findViewById(R.id.btn_login_login);
        btn_signup =  findViewById(R.id.btn_signup_login);

        btn_signup.setOnClickListener(LoginActivity.this);
        btn_login.setOnClickListener(LoginActivity.this);

        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup_login:

                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login_login:
                if ( edt_username.getText().toString().equals("") || edt_password.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this,"Please, fill all blank lines!! ",FancyToast.WARNING,FancyToast.LENGTH_LONG,false).show();
                }
                else {
                    ParseUser.logInInBackground(edt_username.getText().toString(), edt_password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                transactionToSocialMediaActivity();
                                FancyToast.makeText(LoginActivity.this, user.getUsername() + " logged in!!", FancyToast.SUCCESS, FancyToast.LENGTH_LONG, false).show();

                            } else {
                                FancyToast.makeText(LoginActivity.this, "Error: " + e.getMessage(), FancyToast.WARNING, FancyToast.LENGTH_LONG, false).show();
                            }
                        }
                    });
                }
             break;
        }

    }
    public  void rootLayoutTapped(View v){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void transactionToSocialMediaActivity(){
        Intent intent = new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.example.twitterclone;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_username,edt_password;
    private Button btn_signup,btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        edt_username = findViewById(R.id.txt_username);
        edt_password = findViewById(R.id.txt_password);
        btn_login =  findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);


        btn_signup.setOnClickListener(LoginActivity.this);
        btn_login.setOnClickListener(LoginActivity.this);

        if (ParseUser.getCurrentUser() != null){
            changeView();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                if ( edt_password.getText().toString().equals("") || edt_username.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this,"Please fill all blank lines!!",FancyToast.CONFUSING,FancyToast.LENGTH_LONG,false).show();
                }
                else{
                    ParseUser.logInInBackground(edt_username.getText().toString(), edt_password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(e == null && user != null ){
                                changeView();
                                FancyToast.makeText(LoginActivity.this,"Successfull Login!!",FancyToast.SUCCESS,FancyToast.LENGTH_LONG,false).show();
                            }
                            else{
                                FancyToast.makeText(LoginActivity.this,"Error: "+e.getMessage().toString(),FancyToast.ERROR,FancyToast.LENGTH_LONG,false).show();
                            }
                        }
                    });

                }
        }
    }

    public void rootLayoutTapped(View v){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void changeView(){
        Intent intent = new Intent(LoginActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}
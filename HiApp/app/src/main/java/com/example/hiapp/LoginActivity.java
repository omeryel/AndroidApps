package com.example.hiapp;

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

    private EditText edt_username_login,edt_password_login;
    private Button btn_signin,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edt_username_login = findViewById(R.id.edt_username_login);
        edt_password_login = findViewById(R.id.edt_password_login);
        btn_signin = findViewById(R.id.btn_signin_login);
        btn_login = findViewById(R.id.btn_login_login);

        btn_login.setOnClickListener(this);
        btn_signin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            changeView();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_login:
                if(edt_password_login.getText().toString().equals("") || edt_username_login.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this,"Please fill all blank lines!! ",FancyToast.WARNING,FancyToast.LENGTH_SHORT,false).show();
                }else{
                    ParseUser.logInInBackground(edt_username_login.getText().toString(), edt_password_login.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user !=null && e == null){
                                FancyToast.makeText(LoginActivity.this,"Welcome "+ParseUser.getCurrentUser().getUsername().toString(),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                                changeView();
                            }
                            else{
                                FancyToast.makeText(LoginActivity.this,"Error: "+e.getMessage(),FancyToast.WARNING,FancyToast.LENGTH_SHORT,false).show();
                            }
                        }
                    });
                }

                break;
            case R.id.btn_signin_login:
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void changeView(){
        Intent intent = new Intent(LoginActivity.this,UsersActivity.class);
        startActivity(intent);
        finish();
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
}
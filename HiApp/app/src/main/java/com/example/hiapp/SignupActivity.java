package com.example.hiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_username,edt_email,edt_password;
    private Button btn_login,btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        if ( ParseUser.getCurrentUser() != null){
            changeView();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_signup:
                if(edt_email.getText().toString().equals("") || edt_password.getText().toString().equals("") || edt_username.getText().toString().equals("")){
                    FancyToast.makeText(SignupActivity.this,"Please fill blank lines !!",FancyToast.DEFAULT,FancyToast.LENGTH_SHORT,false).show();
                }
                else{
                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(edt_username.getText().toString());
                    appUser.setEmail(edt_email.getText().toString());
                    appUser.setPassword(edt_password.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                FancyToast.makeText(SignupActivity.this,"Welcome "+ParseUser.getCurrentUser().getUsername().toString(),FancyToast.WARNING,FancyToast.LENGTH_SHORT,false).show();
                                changeView();
                            }
                            else{
                                e.printStackTrace();
                                FancyToast.makeText(SignupActivity.this,"Error:" + e.getMessage(),FancyToast.WARNING,FancyToast.LENGTH_SHORT,false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }

    private void changeView(){
        Intent intent = new Intent(SignupActivity.this,UsersActivity.class);
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
package com.example.twitterclone;

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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_password,edt_username,edt_email;
    private Button btn_login,btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        edt_email = findViewById(R.id.txt_email_signup);
        edt_password = findViewById(R.id.txt_password_signup);
        edt_username = findViewById(R.id.txt_username_signup);
        btn_login = findViewById(R.id.btn_login_signup);
        btn_signup = findViewById(R.id.btn_signup_signup);


        btn_signup.setOnClickListener(SignUpActivity.this);
        btn_login.setOnClickListener(SignUpActivity.this);


        if (ParseUser.getCurrentUser() != null){
            changeView();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_signup:
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);

                break;

            case R.id.btn_signup_signup:
                if (edt_username.getText().toString().equals("") && edt_password.getText().toString().equals("") && edt_email.getText().toString().equals(""))
                {
                    FancyToast.makeText(SignUpActivity.this,"Please fill all blank lines!!",FancyToast.WARNING,FancyToast.LENGTH_LONG,false).show();
                }
                else{
                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(edt_username.getText().toString());
                    appUser.setPassword(edt_password.getText().toString());
                    appUser.setEmail(edt_email.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setMessage("Signing up "+ edt_username.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                      @Override
                      public void done(ParseException e) {
                          if(e == null){
                              changeView();
                              FancyToast.makeText(SignUpActivity.this,"Successfully Signed up!!",FancyToast.WARNING,FancyToast.DEFAULT,false).show();

                          }
                          else{
                              e.printStackTrace();
                              FancyToast.makeText(SignUpActivity.this,"Error: "+e.getMessage().toString(),FancyToast.WARNING,FancyToast.LENGTH_LONG,false).show();
                          }
                          progressDialog.dismiss();
                      }
                  });

                }
                break;
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
        Intent intent = new Intent(SignUpActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}
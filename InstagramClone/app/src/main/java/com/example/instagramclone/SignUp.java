package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity  implements View.OnClickListener {

    private Button signup_btn,login_btn;
    private EditText edt_username,edt_password,edt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        signup_btn = findViewById(R.id.btn_signup);
        login_btn = findViewById(R.id.btn_signin);
        edt_email = findViewById(R.id.txt_email);
        edt_password = findViewById(R.id.txt_password);
        edt_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(signup_btn);

                }
                return false;
            }
        });
        edt_username = findViewById(R.id.txt_username);

        signup_btn.setOnClickListener(SignUp.this);
        login_btn.setOnClickListener(SignUp.this);

        if (ParseUser.getCurrentUser() != null){
            //ParseUser.getCurrentUser().logOut();
            transactionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_signup:
                    if (edt_username.getText().toString().equals("")  || edt_password.getText().toString().equals("") || edt_email.getText().toString().equals("") ) {
                        FancyToast.makeText(SignUp.this, "Please fill all blank lines! ", FancyToast.WARNING, FancyToast.LENGTH_LONG, false).show();

                    }
                    else{
                        final ParseUser appUser = new ParseUser();
                        appUser.setEmail(edt_email.getText().toString());
                        appUser.setUsername(edt_username.getText().toString());
                        appUser.setPassword(edt_password.getText().toString());

                        ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                        progressDialog.setMessage("Signing up " + edt_username.getText().toString());
                        progressDialog.show();
                        appUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(SignUp.this, appUser.getUsername() + " signed up!", FancyToast.SUCCESS, FancyToast.LENGTH_LONG, false).show();
                                } else {
                                    FancyToast.makeText(SignUp.this, "Error: " + e.getMessage(), FancyToast.WARNING, FancyToast.LENGTH_LONG, false).show();
                                }
                                progressDialog.dismiss();
                                transactionToSocialMediaActivity();
                            }
                        });
                    }

                    break;
                case R.id.btn_signin:

                    Intent intent = new Intent(SignUp.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

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
        Intent intent = new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}

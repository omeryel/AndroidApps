package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");


        linearLayout = findViewById(R.id.linearLayout);
        //FancyToast.makeText(UsersPosts.this,receivedUserName,FancyToast.CONFUSING,FancyToast.LENGTH_LONG,false).show();

        setTitle(receivedUserName + "'s Posts");
        ParseQuery<ParseObject> parseQuery =new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");

        ProgressDialog progressDialog = new ProgressDialog(UsersPosts.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0){
                    for (ParseObject object : objects){

                        TextView postDesc = new TextView(UsersPosts.this);
                        postDesc.setText(object.get("image_des")+"");
                        ParseFile postPicture = (ParseFile) object.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if ( e == null && data != null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,10);
                                    postDesc.setLayoutParams(des_params);
                                    postDesc.setGravity(Gravity.CENTER);
                                    postDesc.setBackgroundColor(Color.BLUE);
                                    postDesc.setTextColor(Color.WHITE);
                                    postDesc.setTextSize(30f);


                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDesc);



                                }
                                else{
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }else{
                    FancyToast.makeText(UsersPosts.this,receivedUserName+ " has no posts!!",FancyToast.INFO,FancyToast.LENGTH_LONG,false).show();
                    finish();
                }
                progressDialog.dismiss();
            }
        });
    }
}
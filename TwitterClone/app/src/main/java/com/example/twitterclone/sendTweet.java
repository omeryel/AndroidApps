package com.example.twitterclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sendTweet extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_tweet;
    private Button btn_send,btn_list;
    private ListView listTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        setTitle(ParseUser.getCurrentUser().getUsername() +" - Send Tweet");

        btn_send = findViewById(R.id.btn_send_tweet);
        edt_tweet = findViewById(R.id.edt_tweet);
        btn_list = findViewById(R.id.btn_viewTweet);
        listTweet = findViewById(R.id.list_tweet);


        btn_send.setOnClickListener(this::sendTweet);
        btn_list.setOnClickListener(sendTweet.this);


    }

    public void sendTweet(View view){
        ParseObject parseObject = new ParseObject("MyTweet");
        parseObject.put("tweet",edt_tweet.getText().toString());
        parseObject.put("user",ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    FancyToast.makeText(sendTweet.this,ParseUser.getCurrentUser().getUsername()+"'s tweet ( "+ edt_tweet.getText().toString()+" ) is saved!",FancyToast.DEFAULT,FancyToast.LENGTH_LONG,false).show();

                }
                else{
                    FancyToast.makeText(sendTweet.this,e.getMessage()+" ",FancyToast.ERROR,FancyToast.LENGTH_LONG,false).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

        final ArrayList<HashMap<String,String>> tweetList = new ArrayList<>();
        final SimpleAdapter adapter = new SimpleAdapter(sendTweet.this,tweetList,android.R.layout.simple_list_item_2,new String[]{"tweetUserName","tweetValue"},new int[]{android.R.id.text1,android.R.id.text2});
        try{
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("MyTweet");
            parseQuery.whereContainedIn("user", ParseUser.getCurrentUser().getList("fanof"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if( objects.size() > 0 && e ==null){
                        for(ParseObject tweetObject : objects){
                            HashMap<String,String> userTweet = new HashMap<>();
                            userTweet.put("tweetUserName",tweetObject.getString("user"));
                            userTweet.put("tweetValue",tweetObject.getString("tweet"));
                            tweetList.add(userTweet);
                        }
                        listTweet.setAdapter(adapter);
                    }
                    else{
                        FancyToast.makeText(sendTweet.this,"There is no tweet to show!! ",FancyToast.ERROR,FancyToast.LENGTH_LONG,false).show();
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
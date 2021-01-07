package com.example.twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<String> tUsers;
    private ArrayAdapter adapater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);
        setTitle(ParseUser.getCurrentUser().getUsername()+ " - Users");

        FancyToast.makeText(TwitterUsers.this,"Welcome " + ParseUser.getCurrentUser().getUsername().toString(),FancyToast.SUCCESS,FancyToast.LENGTH_SHORT,false).show();

        tUsers = new ArrayList<>();
        listView = findViewById(R.id.list_users);
        adapater = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,tUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(TwitterUsers.this);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername().toString());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null && objects.size() > 0){
                    for ( ParseUser user : objects){
                        tUsers.add(user.getUsername().toString());

                    }
                    listView.setAdapter(adapater);
                    for (String twitterUser : tUsers){
                        if(ParseUser.getCurrentUser().getList("fanof") != null){
                            if( ParseUser.getCurrentUser().getList("fanof").contains(twitterUser)){
                                listView.setItemChecked(tUsers.indexOf(twitterUser),true);

                            }
                        }

                    }

                }
                else{
                    FancyToast.makeText(TwitterUsers.this,"There is no user!!",FancyToast.ERROR,FancyToast.LENGTH_SHORT,false).show();

                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logoutUser){
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent intent =new Intent(TwitterUsers.this,LoginActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.sendTweet){
            Intent intent = new Intent(TwitterUsers.this,sendTweet.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked()){
            FancyToast.makeText(TwitterUsers.this,tUsers.get(position)+" is now followed!",FancyToast.DEFAULT,FancyToast.LENGTH_LONG,false).show();
            ParseUser.getCurrentUser().add("fanof", tUsers.get(position));
        }
        else{
            FancyToast.makeText(TwitterUsers.this,tUsers.get(position)+" is now unfollowed!",FancyToast.DEFAULT,FancyToast.LENGTH_LONG,false).show();

            ParseUser.getCurrentUser().getList("fanof").remove(tUsers.get(position));
            List currentUserFanList = ParseUser.getCurrentUser().getList("fanof");
            ParseUser.getCurrentUser().remove("fanof");
            ParseUser.getCurrentUser().put("fanof",currentUserFanList);
        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    FancyToast.makeText(TwitterUsers.this,"Saved",FancyToast.WARNING,FancyToast.LENGTH_SHORT,false).show();
                }
            }
        });


    }
}
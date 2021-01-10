package com.example.hiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listview;
    private ArrayList<String> tUsers;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

         listview = findViewById(R.id.list_users);
         tUsers = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,tUsers);
        listview.setOnItemClickListener(UsersActivity.this);

        final SwipeRefreshLayout mySwipeRefreshLayout = findViewById(R.id.swipeContainer);

        try {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseUser user : objects) {
                            tUsers.add(user.getUsername());

                        }
                        listview.setAdapter(adapter);

                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                    parseQuery.whereNotContainedIn("username",tUsers);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if(objects.size() > 0 ){
                                if(e == null){
                                    for (ParseUser user : objects){
                                        tUsers.add(user.getUsername());
                                    }
                                    adapter.notifyDataSetChanged();
                                    if (mySwipeRefreshLayout.isRefreshing()){
                                        mySwipeRefreshLayout.setRefreshing(false);
                                    }
                                }


                            }
                            else{
                                if(mySwipeRefreshLayout.isRefreshing()){
                                    mySwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                    });
                }catch (Exception e){

                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(UsersActivity.this,ChatActivity.class);
            intent.putExtra("selectedUser",tUsers.get(position));
            startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Intent intent = new Intent(UsersActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
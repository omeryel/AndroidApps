package com.example.hiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_message;
    private ImageButton btn_send;
    private ListView list_chat;
    private String selected_user;
    private  ArrayList<String> chatsList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btn_send = findViewById(R.id.btn_send_chat);
        edt_message = findViewById(R.id.edt_chat_message);
        selected_user = getIntent().getStringExtra("selectedUser");
        list_chat = findViewById(R.id.list_chat);
        chatsList = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,chatsList);
        list_chat.setAdapter(adapter);
        try {
            ParseQuery<ParseObject> firstUserChatQuery = ParseQuery.getQuery("Chat");
            ParseQuery<ParseObject> secondtUserChatQuery = ParseQuery.getQuery("Chat");

            firstUserChatQuery.whereEqualTo("waSender",ParseUser.getCurrentUser().getUsername());
            firstUserChatQuery.whereEqualTo("WaTarget",selected_user);

            secondtUserChatQuery.whereEqualTo("waSender",selected_user);
            secondtUserChatQuery.whereEqualTo("WaTarget",ParseUser.getCurrentUser().getUsername());

            ArrayList<ParseQuery<ParseObject>> allQueries = new ArrayList<>();
            allQueries.add(firstUserChatQuery);
            allQueries.add(secondtUserChatQuery);

            ParseQuery<ParseObject> myQuery = ParseQuery.or(allQueries);
            myQuery.orderByAscending("CreatedAt");

            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size() > 0 && e == null){
                        for (ParseObject object : objects){
                            String waMessage = object.get("waMessage")+"";
                            if(object.get("waSender").equals(ParseUser.getCurrentUser().getUsername())){
                                waMessage = ParseUser.getCurrentUser().getUsername()+" : "+waMessage;

                            }
                            if (object.get("waSender").equals(selected_user)){
                                waMessage = selected_user + " : " + waMessage;
                            }
                            chatsList.add(waMessage);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }


        btn_send.setOnClickListener(this);






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
                        Intent intent = new Intent(ChatActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_chat:

                ParseObject chat = new ParseObject("Chat");
                chat.put("waSender",ParseUser.getCurrentUser().getUsername());
                chat.put("WaTarget",selected_user);
                chat.put("waMessage",edt_message.getText().toString());
                chat.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            chatsList.add(ParseUser.getCurrentUser().getUsername()+" : "+ edt_message.getText().toString());
                            adapter.notifyDataSetChanged();
                            edt_message.setText("");
                        }
                    }
                });


                break;
        }
    }
}
package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class UsersTab extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {


    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1 ,arrayList);
        listView.setOnItemClickListener(UsersTab.this);
        listView.setOnItemLongClickListener(UsersTab.this);
        final TextView textView = view.findViewById(R.id.txt_loading);

        ParseQuery<ParseUser> parseQuery =ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null){

                    if(objects.size() > 0){
                        for (ParseUser user : objects){

                            arrayList.add(user.getUsername());

                        }
                        listView.setAdapter(arrayAdapter);
                        textView.animate().alpha(0).setDuration(1750);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        return  view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(),UsersPosts.class);
        intent.putExtra("username",arrayList.get(position));
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if(e == null && object != null){

                    //FancyToast.makeText(getContext(),object.get("profile_sport")+"",FancyToast.CONFUSING,FancyToast.LENGTH_LONG,false).show();

                   final PrettyDialog prettyDialog = new PrettyDialog(getContext());

                   prettyDialog.setTitle(object.getUsername()+"'s Info").setMessage(object.get("profile__bio") + "\n"+ object.get("profile_professions")+"\n"+ object.get("profile_hobbies")+"\n"
                   +object.get("profile_sport")).setIcon(R.drawable.person_draw).addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                       @Override
                       public void onClick() {
                           prettyDialog.dismiss();
                       }
                   }).show();

                }
            }
        });

        return true;
    }
}
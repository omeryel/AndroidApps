package com.example.instagramclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileTab extends Fragment {


    private EditText edt_hobbies,edt_profile,edt_bio,edt_sport,edt_profession;
    private Button btn_update;

    public ProfileTab() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edt_profile = view.findViewById(R.id.edt_profileName);
        edt_bio = view.findViewById(R.id.edt_bio);
        edt_hobbies = view.findViewById(R.id.edt_hobbies);
        edt_sport = view.findViewById(R.id.edt_favoriteSport);
        edt_profession = view.findViewById(R.id.edt_profession);
        btn_update = view.findViewById(R.id.btn_update);

       final ParseUser parseUser  = ParseUser.getCurrentUser();

       if(parseUser.get("profileName")  == null){
           edt_profile.setText("");
       }
       else{
           edt_profile.setText(parseUser.get("profileName")+"");

       }
       if(parseUser.get("profile_professions")  == null){
          edt_profession.setText("");
       }
       else{
          edt_profession.setText(parseUser.get("profile_professions")+"");

       }
        if(parseUser.get("profile_sport")  == null){
            edt_sport.setText("");
        }
        else{
            edt_sport.setText(parseUser.get("profile_sport")+"");

        }
        if(parseUser.get("profile_hobbies")  == null){
            edt_hobbies.setText("");
        }
        else{
            edt_hobbies.setText(parseUser.get("profile_hobbies")+"");

        }
        if(parseUser.get("profile__bio")  == null){
            edt_bio.setText("");
        }
        else{
            edt_bio.setText(parseUser.get("profile_bio")+"");

        }




        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName",edt_profile.getText().toString());
                parseUser.put("profile__bio",edt_bio.getText().toString());
                parseUser.put("profile_sport",edt_sport.getText().toString());
                parseUser.put("profile_hobbies",edt_hobbies.getText().toString());
                parseUser.put("profile_professions",edt_profession.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(getContext(),"Info Updated.",FancyToast.INFO,FancyToast.LENGTH_LONG,false).show();
                        }
                        else{
                            FancyToast.makeText(getContext(),"Error: "+ e.getMessage(),FancyToast.ERROR,FancyToast.LENGTH_LONG,false).show();

                        }
                    }
                });
            }
        });
        return view;
    }
}
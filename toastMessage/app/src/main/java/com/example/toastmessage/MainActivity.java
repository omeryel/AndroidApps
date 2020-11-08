package com.example.toastmessage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClicked(View buttonView){
        EditText name = findViewById(R.id.nameBox);
        EditText number = findViewById(R.id.numberBox);
        ImageView image = findViewById(R.id.imageView);


        Toast.makeText(MainActivity.this,"Your name is: "+ name.getText().toString() + "\n Your NUmber is:" + number.getText().toString(),Toast.LENGTH_LONG).show();
        image.setImageResource(R.drawable.vegetable);

    }
}
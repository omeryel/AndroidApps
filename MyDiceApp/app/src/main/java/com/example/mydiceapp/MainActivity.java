package com.example.mydiceapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int [] diceImages = {R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};

        ImageView diceImage01 = findViewById(R.id.imgDice01);
        ImageView diceImage02 = findViewById(R.id.imgDice02);
        Button btnRoll = findViewById(R.id.btnRoll);


        final MediaPlayer mp = MediaPlayer.create(this,R.raw.dice_sound);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("mydiceApp","btn clicked");

                Random rndObject = new Random();

                int myrandNumber = rndObject.nextInt(6);

                Log.i("mydiceApp", String.valueOf(myrandNumber));

                diceImage01.setImageResource(diceImages[myrandNumber]);
                myrandNumber = rndObject.nextInt(6);
                diceImage02.setImageResource(diceImages[myrandNumber]);

                YoYo.with(Techniques.Shake)
                        .duration(450)
                        .repeat(0)
                        .playOn(diceImage01);

                YoYo.with(Techniques.Shake)
                        .duration(450)
                        .repeat(0)
                        .playOn(diceImage02);

                mp.start();
            }
        });
    }
}
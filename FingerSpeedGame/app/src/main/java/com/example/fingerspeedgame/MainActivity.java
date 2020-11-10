package com.example.fingerspeedgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timertext ;
    private TextView thousandtext ;
    private Button tapBtn;
    private long initialCountDown = 60000;
    private  int timerInterval = 1000;
    private CountDownTimer countdownTmr ;
    private  int remainingTime = 60;
    private int aThousand = 10;
    private final String REMAINING_TIME_KEY = "remaining time key";
    private final String A_THOUSAND_KEY = "a thousand key";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("Destroy called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REMAINING_TIME_KEY,remainingTime);
        outState.putInt(A_THOUSAND_KEY,aThousand);
        countdownTmr.cancel();
        showToast("on save instance called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timertext = findViewById(R.id.timerTxt);
        thousandtext =  findViewById(R.id.thousandTxt);
        tapBtn = findViewById(R.id.btnTap);
        showToast("on created called");
        thousandtext.setText(aThousand+"");

        if(savedInstanceState != null){
            remainingTime = savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand = savedInstanceState.getInt(A_THOUSAND_KEY);
            restoreTheGame();
        }

        tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aThousand--;
                thousandtext.setText(aThousand+"");
                if(remainingTime > 0 && aThousand <= 0){
                    Toast.makeText(MainActivity.this,"Congratulations",Toast.LENGTH_LONG).show();
                    showAlert("Congratulations","Would you like to reset game?");

                }
            }
        });
        if (savedInstanceState == null ){

            countdownTmr = new CountDownTimer(initialCountDown,timerInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime = (int)(millisUntilFinished/1000);
                    timertext.setText(remainingTime + "");

                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this,"Countdown Finished",Toast.LENGTH_LONG).show();
                }
            };
            countdownTmr.start();

        }

    }

    private void restoreTheGame() {
        int restoredRemainingTime = remainingTime;
        int restoredAThousand = aThousand;

        timertext.setText(restoredRemainingTime+"");
        thousandtext.setText(restoredAThousand+"");

        countdownTmr  = new CountDownTimer(restoredRemainingTime*1000,timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                    remainingTime = (int)millisUntilFinished / 1000;
                    timertext.setText(remainingTime+"");

            }

            @Override
            public void onFinish() {
                showAlert("Congratulations","Would you like to reset game?");

            }
        };
        countdownTmr.start();
    }

    private void resetTheGame(){
        if(countdownTmr != null){
            countdownTmr.cancel();
            countdownTmr = null;
        }

        remainingTime = 60;
        aThousand = 10;
        thousandtext.setText(Integer.toString(aThousand));
        timertext.setText(remainingTime+"");
        countdownTmr = new CountDownTimer(initialCountDown,timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                      remainingTime =  (int)millisUntilFinished/1000;
                      timertext.setText(remainingTime+"");

            }

            @Override
            public void onFinish() {
                showAlert("Congratulations","Would you like to reset game?");
            }
        };

    }



    private void showAlert(String title,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetTheGame();
                        countdownTmr.start();
                    }
                })
                .show();
        alertDialog.setCancelable(false);
    }

    private void showToast(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.my_item){
            showToast(BuildConfig.VERSION_NAME);
        }
        return  true;
    }
}
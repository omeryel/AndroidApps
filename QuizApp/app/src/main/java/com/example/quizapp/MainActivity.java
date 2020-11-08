package com.example.quizapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final int USER_PROGRESS = 10;

    private Button btnTrue;
    private  Button btnFalse;
    private TextView questions;
    private  TextView stats;
    private int mQuizQuestion;
    private int mQuestionIndex ;
    private ProgressBar mProgressBar;
    private int mUserScore;


    private QuizModel[] questionCollection = new QuizModel[]{
            new QuizModel(R.string.q1,true),
            new QuizModel(R.string.q2,false),
            new QuizModel(R.string.q3,true),
            new QuizModel(R.string.q4,false),
            new QuizModel(R.string.q5,true),
            new QuizModel(R.string.q6,false),
            new QuizModel(R.string.q7,false),
            new QuizModel(R.string.q8,false),
            new QuizModel(R.string.q9,true),
            new QuizModel(R.string.q10,true)

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);
        questions = findViewById(R.id.txtQuestion);
        mProgressBar = findViewById(R.id.progressBar2);
        stats = findViewById(R.id.txtStats);
        QuizModel q1 = questionCollection[mQuestionIndex];
        mQuizQuestion = q1.getmQuestion();

        questions.setText(mQuizQuestion);
        stats.setText(String.valueOf(mUserScore));

        View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    if (v.getId() == R.id.btnTrue){
                        evaluateUserAnswer(true);
                        changeQuestionOnClick();


                    }
                    else if ( v.getId() == R.id.btnFalse){
                        evaluateUserAnswer(false);
                        changeQuestionOnClick();


                    }
                    Toast myToast = Toast.makeText(getApplicationContext(),questions.getText().toString(),Toast.LENGTH_SHORT);
//                    myToast.show();

            }
        };

        btnTrue.setOnClickListener(clickListener);
        btnFalse.setOnClickListener(clickListener);
    }



    private  void changeQuestionOnClick(){
        mQuestionIndex = mQuestionIndex + 1;
        mQuizQuestion = questionCollection[mQuestionIndex % 10].getmQuestion();
        questions.setText(mQuizQuestion);
        mProgressBar.incrementProgressBy(USER_PROGRESS);
        if(mQuestionIndex == 10){
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("The Quiz is finished.");
            quizAlert.setMessage("Your score is " + mUserScore);
            quizAlert.setPositiveButton("Finish Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();
        }

    }

    private  void evaluateUserAnswer(boolean answer){
        boolean realAnswer = questionCollection[mQuestionIndex].ismAnswer();
        if (answer == realAnswer){
            Toast.makeText(getApplicationContext(),R.string.correct_toast_message,Toast.LENGTH_SHORT).show();
            mUserScore  += 1;
            stats.setText(String.valueOf(mUserScore));
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast_message,Toast.LENGTH_SHORT).show();
            mUserScore  -= 1;
            stats.setText(String.valueOf(mUserScore));
        }
    }

}
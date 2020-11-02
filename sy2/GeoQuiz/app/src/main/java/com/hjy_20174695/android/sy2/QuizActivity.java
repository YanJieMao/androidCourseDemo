package com.hjy_20174695.android.sy2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWER = "KEY_ANSWER";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private double correctAnswer = 0; //回答正确个数
    private double answerNum = 0; //回答问题个数

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true, 0),
            new Question(R.string.question_oceans, true, 0),
            new Question(R.string.question_mideast, false, 0),
            new Question(R.string.question_africa, false, 0),
            new Question(R.string.question_americas, true, 0),
            new Question(R.string.question_asia, true, 0),
    };

    private int mCurrentIndex = 0;

    public QuizActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            int[] answerList = savedInstanceState.getIntArray(KEY_ANSWER);
            for(int i = 0; i < mQuestionBank.length; i++){
                mQuestionBank[i].setIsAnswerd(answerList[i]);
                ButtonEnabled();
            }
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                answerNum++;


                if(answerNum == mQuestionBank.length){
                    double i = correctAnswer / mQuestionBank.length;
                    double score = i * 100;
                    NumberFormat nf = new DecimalFormat("##.##");  // 保留小数点后两位
                    String str = nf.format(score);
                    Toast toast = Toast.makeText(QuizActivity.this,"成绩="+str+"%",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();
                }
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrentIndex > 0){
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();


                }


                else{
                    Toast toast = Toast.makeText(QuizActivity.this,"This is page one.",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();
                }




            }
        });

        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        int[] answerList = new int[mQuestionBank.length];
        for(int i = 0; i < answerList.length; i++){
            answerList[i] = mQuestionBank[i].getIsAnswerd();
        }
        savedInstanceState.putIntArray(KEY_ANSWER, answerList);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        ButtonEnabled();
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            mQuestionBank[mCurrentIndex].setIsAnswerd(1);
            messageResId = R.string.correct_toast;
            correctAnswer++; //次数加一

        } else {
            mQuestionBank[mCurrentIndex].setIsAnswerd(-1);
            messageResId = R.string.incorrect_toast;
        }
        ButtonEnabled();

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

    }

    public void ButtonEnabled(){
        if(mQuestionBank[mCurrentIndex].getIsAnswerd()!=0){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);

        }else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }
}

package com.hjy_20174695.android.sy2;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private int IsAnswerd;

    public Question(int textResId, boolean answerTrue, int IsAnswerd) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        IsAnswerd = IsAnswerd;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getIsAnswerd() {
        return IsAnswerd;
    }

    public void setIsAnswerd(int isAnswerd) {
        IsAnswerd = isAnswerd;
    }
}

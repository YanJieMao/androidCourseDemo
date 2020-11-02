package ru.ilyasmirnov.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {

    private static String INSTANCE_STATE = "instance_state";
    private static String SAVED_BOXEN = "boxen";

    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    private boolean mTouch = false;
    private boolean mMultiTouch = false;

    private int[] mIdBank = new int[10];
    private ArrayList <Integer> mIdBankList = new ArrayList<>(10);

    private PointF mCurrent;

    private float mPointerIndex0X;
    private float mPointerIndex0Y;

    private double mDefaultAngle = 0;
    private double mRotateAngle = 0;

    private StringBuilder mSb = new StringBuilder();
    private String mCoordinatesText = "\t" + getResources().getText(R.string.coordinates_text);
    private String mResult = "";

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onCoordinatesUpdated(String coordinates);
    }

    // Используется при создании, представления в коде
    public BoxDrawingView(Context context) {
        this(context, null);
    }

    // Используется при заполнении представления по разметке XML
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Цвет прямоугольников
        mBoxPaint = new Paint();
        mBoxPaint.setColor(getResources().getColor(R.color.colorRectangles));

        // Цвет фона
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(getResources().getColor(R.color.colorRectanglesBackground));

        mCallbacks = (Callbacks) context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // событие
        int actionMask = event.getActionMasked();

        // число касаний
        int pointerCount = event.getPointerCount();

        if (!mMultiTouch) {
            mCurrent = new PointF(event.getX(), event.getY());
        }

        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
                // Сброс текущего состояния
                mCurrentBox = new Box(mCurrent, 0);
                mBoxen.add(mCurrentBox);
                mTouch = true;
                mMultiTouch = false;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mIdBankList.clear();
                mMultiTouch = true;

                if (event.getPointerId(0) == 0 && event.getPointerId(1) == 1) {
                    mIdBankList.add(0,0);
                    mIdBankList.add(1,1);
                    mDefaultAngle = getAngle(event) - mRotateAngle;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                mSb.setLength(0);
                mIdBankList.clear();

                for (int i = 0; i < 5; i++) {
                    mSb.append("Index = " + i);
                    if (i < pointerCount) {
                        mSb.append(", ID = " + event.getPointerId(i));
                        mSb.append(", X = " + event.getX(i));
                        mSb.append(", Y = " + event.getY(i));

                        mIdBank[i] = event.getPointerId(i);
                        mIdBankList.add(event.getPointerId(i));

                    } else {
                        mSb.append(", ID = ");
                        mSb.append(", X = ");
                        mSb.append(", Y = ");
                    }
                    mSb.append("\r\n");
                }

                if (mIdBankList.contains(1)) {
                    mRotateAngle = getAngle(event) - mDefaultAngle;
                }

                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(mCurrent);
                    mCurrentBox.setRotateAngle(mRotateAngle);
                    invalidate();
                }

                if (event.getPointerId(0) != 0 && event.getPointerId(0) != 1) {
                    mCurrentBox = null;
                    mDefaultAngle = 0;
                    mRotateAngle = 0;
                }
                break;

            case MotionEvent.ACTION_UP:
                mCurrentBox = null;

                mTouch = false;
                mMultiTouch = false;
                mDefaultAngle = 0;
                mRotateAngle = 0;

                mSb.setLength(0);

                break;

            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(0) != 0) {
                    mCurrentBox = null;
                    mDefaultAngle = 0;
                    mRotateAngle = 0;
                    break;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;
                break;
        }

        mResult = mCoordinatesText;

        if (mTouch) {
            mResult += "\n" + mSb.toString();
        }

        mCallbacks.onCoordinatesUpdated(mResult);

        return true;
    }

    private double getAngle(MotionEvent event) {

        if (mPointerIndex0X == 0 && mPointerIndex0Y == 0) {
            mPointerIndex0X = event.getX(0);
            mPointerIndex0Y = event.getY(0);
        }

        int pointerIndex = mIdBankList.indexOf(1);

        float pointerIndexX = event.getX(pointerIndex);
        float pointerIndexY = event.getY(pointerIndex);

        double angle = Math.toDegrees(Math.acos((pointerIndexX - mPointerIndex0X)
                / Math.sqrt(Math.pow(pointerIndexX - mPointerIndex0X, 2)
                + Math.pow(pointerIndexY - mPointerIndex0Y, 2))));

        if (mPointerIndex0X <= pointerIndexX && mPointerIndex0Y == pointerIndexY) {
            return 0;
        }

        if (mPointerIndex0X == pointerIndexX && mPointerIndex0Y > pointerIndexY) {
            return 90;
        }

        if (mPointerIndex0X > pointerIndexX && mPointerIndex0Y == pointerIndexY) {
            return 180;
        }

        if (mPointerIndex0X > pointerIndexX && mPointerIndex0Y < pointerIndexY) {
            return 360 - angle;
        }

        if (mPointerIndex0X == pointerIndexX && mPointerIndex0Y < pointerIndexY) {
            return 270;
        }

        if (mPointerIndex0X < pointerIndexX && mPointerIndex0Y < pointerIndexY) {
            return 360 - angle;
        }

        return angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Заполнение фона
        canvas.drawPaint(mBackgroundPaint);

        for(Box box : mBoxen) {

            canvas.rotate( - (float) box.getRotateAngle(), box.getCurrent().x , box.getCurrent().y);

            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);

            canvas.rotate((float) box.getRotateAngle(), box.getCurrent().x , box.getCurrent().y);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());

        bundle.putParcelableArrayList(SAVED_BOXEN, (ArrayList<Box>) mBoxen);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mBoxen = bundle.getParcelableArrayList(SAVED_BOXEN);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        }
    }
}

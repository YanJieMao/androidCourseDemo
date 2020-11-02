package ru.ilyasmirnov.android.draganddraw;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

public class Box implements Parcelable {

    private PointF mOrigin;
    private PointF mCurrent;
    private double mRotateAngle;

    public Box(PointF origin, double rotateAngle) {
        mOrigin = origin;
        mCurrent = origin;
        mRotateAngle = rotateAngle;
    }

    public Box(Parcel in) {
        mOrigin = (PointF) in.readValue(PointF.class.getClassLoader());
        mCurrent = (PointF) in.readValue(PointF.class.getClassLoader());
        mRotateAngle = (double) in.readValue(Double.class.getClassLoader());
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public double getRotateAngle() {
        return mRotateAngle;
    }

    public void setRotateAngle(double rotateAngle) {
        mRotateAngle = rotateAngle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mOrigin);
        dest.writeValue(mCurrent);
        dest.writeValue(mRotateAngle);
    }

    public static final Parcelable.Creator<Box> CREATOR = new Parcelable.Creator<Box>() {
        @Override
        public Box createFromParcel(Parcel in) {
            return new Box(in);
        }

        @Override
        public Box[] newArray(int size) {
            return new Box[size];
        }
    };
}
package com.moskitol.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    private View mReflectionView;
    private View mSeaView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private ObjectAnimator mReflectionAnimator;
    private ObjectAnimator mHeightAnimator;
    private ObjectAnimator mSunsetSkyAnimator;
    private ObjectAnimator mNightSkyAnimator;
    private AnimatorSet mAnimatorSet;

    private boolean isSunset = true;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);
        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);
        mReflectionView = view.findViewById(R.id.reflection);
        mSeaView = view.findViewById(R.id.sea);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);


        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSunset) {
                    sunsetAnimation();
                } else {
//                    sunriseAnimation();
                    reverseAnimation();
                }
            }
        });

        return view;
    }

    private void sunsetAnimation() {
        isSunset = false;
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        float refractionYStart = mReflectionView.getTop();
        float refractionYEnd = mSkyView.getTop() - mSkyView.getBottom()
                + (mSunView.getBottom() - mSunView.getTop());

        sunAnimation();

        mReflectionAnimator = ObjectAnimator
                .ofFloat(mReflectionView, "y", refractionYStart, refractionYEnd);
        mReflectionAnimator.setDuration(4500);
        mReflectionAnimator.setInterpolator(new AccelerateInterpolator());

        mHeightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        mHeightAnimator.setInterpolator(new AccelerateInterpolator());

        mSunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        mSunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        mNightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        mNightSkyAnimator.setEvaluator(new ArgbEvaluator());

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet
                .play(mHeightAnimator)
                .with(mSunsetSkyAnimator)
                .with(mReflectionAnimator)
                .before(mNightSkyAnimator);
        mAnimatorSet.start();


    }

//    private void sunriseAnimation() {
//        float sunYStart = mSkyView.getHeight();
//        float sunYEnd = mSunView.getTop();
//
//        float refractionYEnd = -50;
//        System.out.println(mSkyView.getBottom() / 3 + "-----------" +mReflectionView.getTop());
//        float refractionYStart = 350;
//
//        sunAnimation();
//
//        ObjectAnimator reflectionAnimation = ObjectAnimator
//                .ofFloat(mReflectionView, "y", refractionYStart, refractionYEnd);
//        reflectionAnimation.setDuration(500);
//        reflectionAnimation.setInterpolator(new AccelerateInterpolator());
//
//        ObjectAnimator heightAnimator = ObjectAnimator
//                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
//                .setDuration(3000);
//        heightAnimator.setInterpolator(new AccelerateInterpolator());
//
//        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
//                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
//                .setDuration(3000);
//        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());
//
//        ObjectAnimator nightSkyAnimator = ObjectAnimator
//                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor )
//                .setDuration(1500);
//        nightSkyAnimator.setEvaluator(new ArgbEvaluator());
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet
//                .play(heightAnimator)
//                .with(nightSkyAnimator)
//                .with(reflectionAnimation)
//                .before(sunsetSkyAnimator);
//        animatorSet.start();
//
//        isSunset = true;
//    }

    private void reverseAnimation() {

        isSunset = !isSunset;

            mHeightAnimator.reverse();
            mHeightAnimator.setStartDelay(1000);

            mReflectionAnimator.reverse();
            mReflectionAnimator.setStartDelay(1000);

            mSunsetSkyAnimator.reverse();
            mSunsetSkyAnimator.setStartDelay(1000);

            mNightSkyAnimator.reverse();
            mSunsetSkyAnimator.setStartDelay(1000);



    }

    private void sunAnimation() {
        float sunXLeft = mSunView.getX();
        float reflectionXLeft = mReflectionView.getX();

        ObjectAnimator sunWidthAnimator = ObjectAnimator
                .ofFloat(mSunView, "x", sunXLeft, sunXLeft + 1)
                .setDuration(150);
        sunWidthAnimator.setInterpolator(new AccelerateInterpolator());
        sunWidthAnimator.setRepeatCount(Integer.MAX_VALUE);

        ObjectAnimator reflectionWidthAnimator = ObjectAnimator
                .ofFloat(mReflectionView, "x", reflectionXLeft, reflectionXLeft + 1)
                .setDuration(150);
        reflectionWidthAnimator.setInterpolator(new AccelerateInterpolator());
        reflectionWidthAnimator.setRepeatCount(Integer.MAX_VALUE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(sunWidthAnimator)
                .with(reflectionWidthAnimator);
        animatorSet.start();
    }
}

package com.bignerdranch.android.sunset;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.sunset.SingleFragmentActivity;
import com.bignerdranch.android.sunset.SunsetFragment;

public class SunsetActivity extends SingleFragmentActivity {
    private static final String TAG = SunsetActivity.class.getSimpleName();

    @Override
    public Fragment createFragment() {
        return SunsetFragment.newInstance();
    }
}

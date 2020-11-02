package com.hjy20174695.android.sy5;

import androidx.fragment.app.Fragment;

public class SunsetActivity extends SingleFragmentActivity {
    private static final String TAG = SunsetActivity.class.getSimpleName();

    @Override
    public Fragment createFragment() {
        return SunsetFragment.newInstance();
    }
}

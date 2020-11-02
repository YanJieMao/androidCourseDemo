package ru.ilyasmirnov.android.draganddraw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class DragAndDrawActivity extends SingleFragmentActivity
        implements BoxDrawingView.Callbacks {

    @Override
    protected Fragment createFragment() {
        return DragAndDrawFragment.newInstance();
    }

    protected Fragment createDetailFragment() {
        return DetailFragment.newInstance();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_twopane;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();

        Fragment detailFragment = fm.findFragmentById(R.id.detail_fragment_container);
        if (detailFragment == null) {
            detailFragment = createDetailFragment();
            fm.beginTransaction()
                    .add(R.id.detail_fragment_container, detailFragment)
                    .commit();
        }
    }

    @Override
    public void onCoordinatesUpdated(String text) {

        FragmentManager fm = getSupportFragmentManager();
        DetailFragment detailFragment = (DetailFragment) fm.findFragmentById(R.id.detail_fragment_container);
        if (detailFragment == null) {
            detailFragment = (DetailFragment) createDetailFragment();
            fm.beginTransaction()
                    .add(R.id.detail_fragment_container, detailFragment)
                    .commit();
        }
        detailFragment.setCoordinates(text);
    }
}



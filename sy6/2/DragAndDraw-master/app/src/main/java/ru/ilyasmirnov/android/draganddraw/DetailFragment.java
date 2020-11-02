package ru.ilyasmirnov.android.draganddraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private TextView mCoordinatesTextView;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        String coordinatesText = "\t" + getResources().getText(R.string.coordinates_text);

        mCoordinatesTextView = v.findViewById(R.id.coordinates_text_view);
        mCoordinatesTextView.setText(coordinatesText);

        return v;
    }

    public void setCoordinates(String coordinates) {
        mCoordinatesTextView.setText(coordinates);
    }
}

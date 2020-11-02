package com.hjy20174695.android.sy4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {
    private AudioPlayer mPlayer = new AudioPlayer();

    private Button mPlayButton;
    private Button mStopButton;
    private Button mPauseButton;
    private Button mVideoButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hello_moon, container, false);

        mPlayButton = (Button)v.findViewById(R.id.hellomoon_playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.play(getActivity());
            }
        });

        mPauseButton = (Button)v.findViewById(R.id.hellomoon_pauseButton);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
            }
        });
        mStopButton = (Button)v.findViewById(R.id.hellomoon_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
            }
        });

        mVideoButton = (Button)v.findViewById(R.id.hellomoon_videoButton);
        mVideoButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.stop();
                Intent intent = new Intent(getActivity(),VideoActivity.class);

                startActivity(intent);
            }
        });






        return v;
    }


    @Override
    public void onPause(){
        super.onPause();
        mPlayer.pause();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }



}

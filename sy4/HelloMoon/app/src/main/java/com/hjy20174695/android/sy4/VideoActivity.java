package com.hjy20174695.android.sy4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends ActionBarActivity {


    private VideoView mVideoView;
    private Button mPauseButton;
    private Button mBackButton;
    private Boolean mIsPause =false; //是否是暂停状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        mVideoView =(VideoView) findViewById(R.id.videoView);
        mVideoView.setMediaController(new MediaController(this));
        String url="android.resource://"+getPackageName()+"/"+R.raw.apollo_17_stroll_video;
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();

        //返回播放
        mBackButton =(Button) findViewById(R.id.backButton);
        mBackButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(VideoActivity.this,HelloMoonActivity.class);
                startActivity(intent);
            }
        });
        //暂停/开始
        mPauseButton = (Button) findViewById(R.id.pauseButton);
        mPauseButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mIsPause){
                    mVideoView.start();
                    mIsPause = false;
                }else{
                    mVideoView.pause();
                    mIsPause =true;
                }
            }
        });



    }





}

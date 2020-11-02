package com.hjy20174695.android.sy7;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText mEditWeather;
    TextView mTextCityname,mTextTemp;
    ImageView mImageWeather;
    Button  mButtonFresh;
    WeatherTask mCurrentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonFresh = (Button) findViewById(R.id.btn);
        mEditWeather = (EditText) findViewById(R.id.edit_weather);
        mImageWeather = (ImageView) findViewById(R.id.weather_pic);
        mTextCityname = (TextView) findViewById(R.id.txt_cityname);
        mTextTemp = (TextView) findViewById(R.id.txt_temp);

        mButtonFresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentTask = new WeatherTask();
                mCurrentTask.execute(mEditWeather.getText().toString());
            }
        });
    }

    @Override
    protected  void onDestroy(){
        if (mCurrentTask!=null){
            mCurrentTask.cancel(true);
            mCurrentTask = null;
        }
        super.onDestroy();
    }

    class WeatherTask extends AsyncTask<String,Void,DataGet.WeatherResult> {
        @Override
        protected DataGet.WeatherResult doInBackground(String... params){

            DataGet dataGet = new DataGet();
            return dataGet.getWeatherData(params[0]);

        }

        @Override
        protected  void onPostExecute(DataGet.WeatherResult weatherResult){
            super.onPostExecute(weatherResult);
            if (weatherResult.getErrorCode() == 0){
                DecimalFormat df = new DecimalFormat("00.00");
                String temp = df.format(weatherResult.getCurTemp());
                mTextTemp.setText(temp+"℃");
                mTextCityname.setText("City: "+weatherResult.getCityName());
                mImageWeather.setImageBitmap(weatherResult.getWeatherIcon());
            }
        }
    }

}
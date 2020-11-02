package com.hjy20174695.android.sy7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class DataGet {

    public class WeatherResult{

        private float curTemp;
        private int errorCode;
        private String cityName;
        private Bitmap weatherIcon;

        public float getCurTemp() {
            return curTemp;
        }

        public void setCurTemp(float curTemp) {
            this.curTemp = curTemp;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Bitmap getWeatherIcon() {
            return weatherIcon;
        }

        public void setWeatherIcon(Bitmap weatherIcon) {
            this.weatherIcon = weatherIcon;
        }

        private  float tranformToC(float abs){
            return (abs-273.15f);
        }

        private Bitmap getWeatherPic(String picName){
            String url = "http://openweathermap.org/img/w/"+picName+".png";
            return downloadPic(url);
        }
        private  Bitmap downloadPic(String url1){
            if (url1 == null){
                return null;
            }
            try {
                URL url = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                InputStream is = connection.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                is.close();
                return bm;

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

    }


    private  static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private  static final String MYKEY = "1b87fb9151d3f2da484743d38bdc3d1c";

    public WeatherResult getWeatherData(String cityname) {
        WeatherResult weatherResult = new WeatherResult();
        try {
            String urlstring = WEATHER_URL + "q=" + cityname + "&APPID=" + MYKEY;
            Log.i("DataGet", urlstring);
            URL url = new URL(urlstring);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            int c = 0;
            while ((c = in.read()) != -1) {
                stringBuffer.append((char) c);
            }
            Log.i("DataGet", stringBuffer.toString());

            WeatherParse weatherParse = new WeatherParse();
            weatherParse.setData(stringBuffer.toString());
            weatherResult.setCurTemp(weatherResult.tranformToC(weatherParse.getCurTemp()));
            weatherResult.setCityName(cityname);
            weatherResult.setWeatherIcon(weatherResult.getWeatherPic(weatherParse.getPicCode()));
            weatherResult.setErrorCode(0);
            return  weatherResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
      return weatherResult;
    }
}

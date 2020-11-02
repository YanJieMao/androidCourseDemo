package com.hjy20174695.android.sy7;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class WeatherParse {

    private float curTemp = 0;
    private String picCode = null;

    private static final String MAIN_KEY ="main";
    private static final String CUR_TEMP_KEY = "temp";
    private static final String WEATHER_KEY = "weather";
    private static final String PIC_CODE_KEY = "icon";

    public void setData( String weatherInfo){
    try {
        JSONObject object = new JSONObject(weatherInfo);
        object = object.getJSONObject(MAIN_KEY);
        curTemp =(float)object.getDouble(CUR_TEMP_KEY);
        JSONObject object1 =new JSONObject(weatherInfo);
        JSONArray ja = object1.getJSONArray(WEATHER_KEY);
        if (ja.length() == 0){
            picCode =null;
        }else{
            picCode = ja.getJSONObject(0).getString(PIC_CODE_KEY);
        }
    }catch (JSONException e){
        e.printStackTrace();
    }
    }
   public float getCurTemp(){
       return curTemp;
   }

    public String getPicCode() {
        return picCode;
    }
}

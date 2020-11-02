package com.hjy20174695.android.sy8;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

public class MainActivity extends AppCompatActivity {
    MapView mMapView = null;
    private AMap aMap;
    private Button yeJian;
    private Button biaoZhun;
    private Button weiXing;

    //private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.map);
        yeJian = (Button)findViewById(R.id.button3);
        weiXing = (Button)findViewById(R.id.button2);
        biaoZhun = (Button)findViewById(R.id.button1);


        yeJian.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this,"正在切换至 夜间地图，请稍后", Toast.LENGTH_LONG).show();
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);
            }
        });
        weiXing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "正在切换至 卫星地图 ，请稍候 ", Toast.LENGTH_LONG).show();
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
            }
        });
        biaoZhun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "正在切换至 标准地图 ，请稍候 ", Toast.LENGTH_LONG).show();
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            }
        });



        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}

package com.example.androidmap;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //jt7bmvxPkHhx0jujHg8UX9H5KRgcTPVK
    TextureMapView mMapView;
    BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (TextureMapView) findViewById(R.id.baidumap);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(mListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setScanSpan(10000);
        option.setAddrType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    LocationClient mLocationClient;
    BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            MyLocationData locationData = new MyLocationData.Builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locationData);
            Log.e("TAG", ""+location.getLocType()+"维度：" + location.getLatitude() + " 经度：" + location.getLongitude());
            Toast.makeText(MainActivity.this, "" + location.getAddrStr(), Toast.LENGTH_SHORT).show();

            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(),
                    location.getLongitude())));

        }
    };


    private void addPoly() {
        PolygonOptions opt = new PolygonOptions();
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(39.965, 116.404));
        points.add(new LatLng(39.925, 116.454));
        points.add(new LatLng(39.955, 116.494));
        points.add(new LatLng(39.905, 116.554));
        points.add(new LatLng(39.965, 116.604));
        opt.points(points);
        mBaiduMap.addOverlay(opt);
    }

    private void addMarker() {
        MarkerOptions opt = new MarkerOptions();
        opt.position(new LatLng(39.963175, 116.400244));
        opt.draggable(true);
        opt.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marke));
        mBaiduMap.addOverlay(opt);

    }

    private void addText() {
        TextOptions opt = new TextOptions();
        opt.text("文字覆盖物");
        opt.fontColor(Color.RED);
        opt.fontSize(100);
        opt.position(new LatLng(39.963175, 116.400244));
        mBaiduMap.addOverlay(opt);
    }

    @Override
    protected void onDestroy() {

        mMapView.onDestroy();
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

}

package com.example.baidu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;

public class MainActivity extends AppCompatActivity {

    TextureMapView mMapView;
    BaiduMap mBaiduMap;
    LocationClient mLocationClient;
    Button btn;
    boolean isLoced = true;
    EditText mEdt;
    Button mBtnSearch;
    RadioGroup mRgp;
    Button mBtnPlan;
    LatLng mLatLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (TextureMapView) findViewById(R.id.map);
        btn = (Button) findViewById(R.id.btn);
        mEdt = (EditText) findViewById(R.id.edt_search);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mRgp = (RadioGroup) findViewById(R.id.rgp);
        mBtnPlan = (Button) findViewById(R.id.btn_plan);
        mBaiduMap = mMapView.getMap();
//        location();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoced = true;
                onLoc();
            }
        });
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEdt.getText().toString();
                if (!str.equals("")) {
                    poi(str);
                }
            }
        });

        mBtnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEdt.getText().toString();
                if (!str.equals("")) {
                    driveRoute(str);
                }
            }
        });
        mRgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String str = mEdt.getText().toString();
                if (!str.equals(""))
                    switch (checkedId) {
                        case R.id.rbtn1:
                            driveRoute(str);
                            break;
                        case R.id.rbtn2:
                            busRoute(str);
                            break;
                        case R.id.rbtn3:
                            walkRoute(str);
                            break;
                    }
            }
        });
    }

    public void poi(String str) {
        PoiParaOption poiParaOption = new PoiParaOption();
        poiParaOption.center(null != mLatLng ? mLatLng : new LatLng(39.965, 116.404));
        poiParaOption.key(str);
        poiParaOption.radius(5000);
        BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(poiParaOption, this);
    }

    String start;

    public void driveRoute(String end) {
        RouteParaOption option = new RouteParaOption();
        option.startName(null != start ? start : "西工大");
        option.startPoint(null != mLatLng ? mLatLng : new LatLng(39.965, 116.404));
        option.endName(end);
        BaiduMapRoutePlan.openBaiduMapDrivingRoute(option, this);
    }

    public void walkRoute(String end) {
        RouteParaOption option = new RouteParaOption();
        option.startName(null != start ? start : "西工大");
        option.startPoint(null != mLatLng ? mLatLng : new LatLng(39.965, 116.404));
        option.endName(end);
        BaiduMapRoutePlan.openBaiduMapWalkingRoute(option, this);
    }

    public void busRoute(String end) {
        RouteParaOption option = new RouteParaOption();
        option.startName(null != start ? start : "西工大");
        option.startPoint(null != mLatLng ? mLatLng : new LatLng(39.965, 116.404));
        option.endName(end);
        BaiduMapRoutePlan.openBaiduMapTransitRoute(option, this);
    }

    final static int REQUEST_CODE_ASK_LOCATION = 123;

    public void onLoc() {
        if (Build.VERSION.SDK_INT >= 23) {
            int locPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (locPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_LOCATION);
            } else {
                location();
            }
        } else {
            location();
        }
    }

    private void location() {
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(this);

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setAddrType("bd09ll");
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.e("TAG", "" + bdLocation.getLocType());
                MyLocationData data = new MyLocationData.Builder()
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                start = bdLocation.getAddrStr();
                mBaiduMap.setMyLocationData(data);
                if (isLoced) {
                    isLoced = false;
                    mLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());

                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(mLatLng));
                }

            }
        });

        mLocationClient.start();
//        mBaiduMap.
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

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    location();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "权限请求失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

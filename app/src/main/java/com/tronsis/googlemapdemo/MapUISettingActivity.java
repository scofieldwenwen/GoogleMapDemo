package com.tronsis.googlemapdemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapUISettingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings uiSettings;
    private CheckBox mCbZoomBtn;
    private CheckBox mCbCompassBtn;
    private CheckBox mCbMyLocationBtn;
    private CheckBox mCbMyLocationLayerBtn;
    private CheckBox mCbMaptoolBtn;
    private CheckBox mCbScrollGestures;
    private CheckBox mCbZoomlGestures;
    private CheckBox mCbTiltGestures;
    private CheckBox mCbRotateGestures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_uisetting);

        bindView();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_ui);
        mapFragment.getMapAsync(this);
    }

    private void bindView() {
        mCbZoomBtn = (CheckBox) findViewById(R.id.zoom_buttons_toggle);
        mCbCompassBtn = (CheckBox) findViewById(R.id.compass_toggle);
        mCbMyLocationBtn = (CheckBox) findViewById(R.id.mylocationbutton_toggle);
        mCbMyLocationLayerBtn = (CheckBox) findViewById(R.id.mylocationlayer_toggle);
        mCbMaptoolBtn = (CheckBox) findViewById(R.id.googlemap_tools);
        mCbScrollGestures = (CheckBox) findViewById(R.id.scroll_toggle);
        mCbZoomlGestures = (CheckBox) findViewById(R.id.zoom_gestures_toggle);
        mCbTiltGestures = (CheckBox) findViewById(R.id.tilt_toggle);
        mCbRotateGestures = (CheckBox) findViewById(R.id.rotate_toggle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uiSettings = mMap.getUiSettings();
        addDefualtMarker();
        initMapUI();
    }

    private void initMapUI() {
        //放大缩小按键显示
        uiSettings.setZoomControlsEnabled(mCbZoomBtn.isChecked());
        //指南针显示,旋转地图后才能看见
        uiSettings.setCompassEnabled(mCbCompassBtn.isChecked());
        //定位按钮显示
        uiSettings.setMyLocationButtonEnabled(mCbMyLocationBtn.isChecked());
        //定位权限判断
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(MapUISettingActivity.this, "请打开定位服务", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //定位楼层显示
            mMap.setMyLocationEnabled(mCbMyLocationLayerBtn.isChecked());
        }
        //地图工具按钮(选中标记才有显示)
        uiSettings.setMapToolbarEnabled(mCbMaptoolBtn.isChecked());
        //地图滑动手势
        uiSettings.setScrollGesturesEnabled(mCbScrollGestures.isChecked());
        //地图缩放手势
        uiSettings.setZoomGesturesEnabled(mCbZoomlGestures.isChecked());
        //地图倾斜手势
        uiSettings.setTiltGesturesEnabled(mCbTiltGestures.isChecked());
        //地图旋转手势
        uiSettings.setRotateGesturesEnabled(mCbRotateGestures.isChecked());

    }


    private boolean isMapReady() {
        if (mMap == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setZoomButtonsEnabled(View view) {
        if (!isMapReady()) {
            return;
        }
        uiSettings.setZoomControlsEnabled(mCbZoomBtn.isChecked());
    }

    public void setCompassEnabled(View view) {
        if (!isMapReady()) {
            return;
        }
        uiSettings.setCompassEnabled(mCbCompassBtn.isChecked());
    }

    public void setMyLocationButtonEnabled(View view) {
        if (!isMapReady()) {
            return;
        }
        uiSettings.setMyLocationButtonEnabled(mCbMyLocationBtn.isChecked());
    }

    public void setMyLocationLayerEnabled(View view) {
        if (!isMapReady()) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(MapUISettingActivity.this, "请打开定位服务", Toast.LENGTH_SHORT).show();
            mCbMyLocationLayerBtn.setChecked(false);
            return;
        }else {
//            mCbMyLocationLayerBtn.setChecked(true);
        }
        mMap.setMyLocationEnabled(mCbMyLocationLayerBtn.isChecked());
    }

    public void setMaptoolsEnabled(View view) {
        if (!isMapReady()) {
            return;
        }
        Toast.makeText(MapUISettingActivity.this, "选中标记才有显示", Toast.LENGTH_SHORT).show();
        uiSettings.setMapToolbarEnabled(mCbMaptoolBtn.isChecked());
    }
    public void setScrollGesturesEnabled(View view) {
        if (!isMapReady()) {
            return;
        }
        uiSettings.setScrollGesturesEnabled(mCbScrollGestures.isChecked());
    }

    public void setZoomGesturesEnabled(View view){
        if (!isMapReady()) {
            return;
        }
        //地图缩放手势
        uiSettings.setZoomGesturesEnabled(mCbZoomlGestures.isChecked());
    }

    public void setTiltGesturesEnabled(View view) {
        if (!isMapReady()) {
            return;
        }  //地图倾斜手势
        uiSettings.setTiltGesturesEnabled(mCbTiltGestures.isChecked());
    }

    public void setRotateGesturesEnabled(View view) {
        if (!isMapReady()) {
            return;
        } //地图旋转手势
        uiSettings.setRotateGesturesEnabled(mCbRotateGestures.isChecked());
    }

    /**
     * 添加默认的标记
     */
    private void addDefualtMarker() {
        //坐标经纬度
        LatLng latLng = new LatLng(22.5476226, 113.9440839);
        //maker的属性
        MarkerOptions options = new MarkerOptions().position(latLng).title("Marker").snippet("创火科技");
        //添加marker
        mMap.addMarker(options);
    }


}

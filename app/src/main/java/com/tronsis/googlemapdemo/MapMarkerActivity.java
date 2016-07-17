package com.tronsis.googlemapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 加载基本地图,添加一个marker
 *
 * @author scofield@tronsis.com
 * @date 2016/7/11 11:59
 */
public class MapMarkerActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowCloseListener,
        GoogleMap.OnInfoWindowLongClickListener, GoogleMap.OnMarkerDragListener
{
    private GoogleMap mMap;
    private CustomInfoWindowAdapter infoAdapter;//标记信息窗适配器
    private CheckBox cbInfo;
    private CheckBox cbInfoShow;
    private boolean isShowInfoWindow;
    private Marker defaultMarker;
    private Marker cuttomMarker;
    private Marker infoMarker;
    private Marker dragMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_map);
        cbInfo = (CheckBox) findViewById(R.id.cb_info);
        cbInfoShow = (CheckBox) findViewById(R.id.cb_show_info);
        //获取地图
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    /**
     * 地图加载后回调,对地图进行操作
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //移动摄像头到指定坐标,指定缩放级别
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.915378, 116.393058), 3));
        addDefualtMarker();
        addCustoMarker();
        //设置marker点击事件
        mMap.setOnMarkerClickListener(this);

        //设置info监听事件
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowLongClickListener(this);
    }

    /**
     * 添加默认的标记
     */
    private void addDefualtMarker() {
        //坐标经纬度
        LatLng latLng = new LatLng(22.5476226, 113.9440839);
        //maker的属性
        MarkerOptions options = new MarkerOptions().position(latLng).title("标题:创获科技").snippet("内容：东方科技大厦");
        //添加marker
        defaultMarker = mMap.addMarker(options);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(31.235811, 121.484836));
        infoMarker = mMap.addMarker(markerOptions);
        infoMarker.setTitle("InfoWindow 点击事件");
        infoMarker.setSnippet("1.点击 2.长按 3.关闭");


        markerOptions.position(new LatLng(30.783952, 104.063859));
        markerOptions.title("可以拖动的Marker").snippet("拖动监听事件");
        markerOptions.draggable(true);
        dragMarker = mMap.addMarker(markerOptions);

    }


    /**
     * 添加自定义样式的marker
     */
    private void addCustoMarker() {
        LatLng beijing = new LatLng(39.915378, 116.393058);//北京
        MarkerOptions options = new MarkerOptions().position(beijing).title("这是标题").snippet("这是内容");
        cuttomMarker = mMap.addMarker(options);
        cuttomMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.positon_bg));
        setCustomInfoMarker(cuttomMarker);
    }


    /**
     * 自定义
     *
     * @param ma
     */
    private void setCustomInfoMarker(Marker ma) {
        infoAdapter = new CustomInfoWindowAdapter(this);//marker信息窗
        View view = infoAdapter.getInfoWindow(ma);
        TextView tvStep = ((TextView) view.findViewById(R.id.tv_pet_steps));
        TextView tvPower = ((TextView) view.findViewById(R.id.tv_pet_power));
        TextView tvLed = ((TextView) view.findViewById(R.id.tv_pet_led));
        TextView tvMode = ((TextView) view.findViewById(R.id.tv_pet_mode));
        TextView tvFence = ((TextView) view.findViewById(R.id.tv_pet_fence));
        tvStep.setText("Steps:40");
        tvPower.setText("Power:50%");
        tvMode.setText("Normal");
        tvFence.setText("No Fence");
        tvLed.setText("Off");

    }

    public void onInfoWindowCheckBoxClick(View view) {
        if (mMap == null) {
            return;
        }

        if (cbInfo.isChecked()) {
            //使用自定义的infowindow
            mMap.setInfoWindowAdapter(infoAdapter);
        } else {
            //使用默认的infowindow
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    return null;
                }
            });
        }
    }

    public void onInfoWindowShowClick(View view) {
        if (mMap == null) {
            return;
        }
        isShowInfoWindow = cbInfoShow.isChecked();
    }


    /**
     * 标记点击事件
     *
     * @param marker
     * @return 是否隐藏信息窗
     */
    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.equals(defaultMarker)) {
            Toast.makeText(MapMarkerActivity.this, "默认的标记：点击事件", Toast.LENGTH_SHORT).show();
        }

        if (marker.equals(cuttomMarker)) {
            Toast.makeText(MapMarkerActivity.this, "自定义的标记：点击事件", Toast.LENGTH_SHORT).show();
        }

        return isShowInfoWindow;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (infoMarker.equals(marker)) {
            Toast.makeText(MapMarkerActivity.this, "onInfoWindowClick", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        if (infoMarker.equals(marker)) {
            Toast.makeText(MapMarkerActivity.this, "onInfoWindowClose", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        if (infoMarker.equals(marker)) {
            Toast.makeText(MapMarkerActivity.this, "onInfoWindowLongClick", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //marker 拖动开始
        dragMarker.setSnippet(dragMarker.getPosition().toString());
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        //marker 拖动
        dragMarker.setSnippet(dragMarker.getPosition().toString());
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //marker 拖动结束
        dragMarker.setSnippet(dragMarker.getPosition().toString());
    }
}

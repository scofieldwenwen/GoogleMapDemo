package com.tronsis.googlemapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
public class MapMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private CustomInfoWindowAdapter infoAdapter;//标记信息窗适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_map);

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
        addDefualtMarker();
        addCustoMarker();
    }

    /**
     * 添加默认的标记
     */
    private void addDefualtMarker() {
        //坐标经纬度
        LatLng latLng = new LatLng(22.5476226, 113.9440839);
        //maker的属性
        MarkerOptions options = new MarkerOptions().position(latLng).title("Marker title").snippet("创火科技");
        //添加marker
        mMap.addMarker(options);
    }


    private void addCustoMarker() {
        LatLng beijing = new LatLng(39.915378, 116.393058);//北京
        MarkerOptions options = new MarkerOptions().position(beijing);
        Marker cuttomMarker = mMap.addMarker(options);
        cuttomMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_bg));
        setCustomInfoMarker(cuttomMarker);
    }


    private void setCustomInfoMarker(Marker ma) {
        infoAdapter = new CustomInfoWindowAdapter(this);//marker信息窗
        View view = infoAdapter.getInfoWindow(ma);
        TextView tvStep = ((TextView) view.findViewById(R.id.tv_pet_steps));
        TextView tvPower = ((TextView) view.findViewById(R.id.tv_pet_power));
        TextView tvLed = ((TextView) view.findViewById(R.id.tv_pet_led));
        TextView tvMode = ((TextView) view.findViewById(R.id.tv_pet_mode));
        TextView tvFence = ((TextView) view.findViewById(R.id.tv_pet_fence));
        tvStep.setText("40");
        tvPower.setText("50%");
        tvMode.setText("Normal");
        tvFence.setText("No Fence");
        tvLed.setText("Off");
        mMap.setInfoWindowAdapter(infoAdapter);
    }
}

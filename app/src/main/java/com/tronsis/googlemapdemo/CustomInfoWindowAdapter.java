package com.tronsis.googlemapdemo;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * marker信息窗adapter
 * Demonstrates customizing the info window and/or its contents.
 *
 * @author scofield@tronsis.com
 * @date 2016/5/13 10:36
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;

    public CustomInfoWindowAdapter(Activity activity) {
        mWindow = activity.getLayoutInflater().inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


}

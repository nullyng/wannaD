package com.example.wannad.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.wannad.R;

import net.daum.mf.map.api.MapView;

public class MapFragment  extends Fragment {
    String id;
    String lon, lat;
    String place_name;
    String key = "a9cbc40f5f92bebee9c7bf20723d3b2a";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // 지도 객체 생성
        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup)v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 현위치 트래킹 모드 설정
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        return v;
    }
}
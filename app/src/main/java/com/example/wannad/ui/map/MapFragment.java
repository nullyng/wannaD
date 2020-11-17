package com.example.wannad.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.wannad.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapFragment  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // 지도
        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup)v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 중심점 변경 = 예제 좌표는 서울 남산
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.54892296550104,
                126.99089033876304), true);

        // 줌 레벨 변경
        mapView.setZoomLevel(4, true);

        // 마커 찍기
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104,
                126.99089033876304);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        // 기본으로 제공하는 BluePin 마커 모양
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        // 마커를 클릭했을 때, 기본으로 제공하는 RedPin 마커 모양
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);

        return v;
    }
}
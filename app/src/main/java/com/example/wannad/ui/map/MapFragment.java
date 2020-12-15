package com.example.wannad.ui.map;

import com.example.wannad.BottomNavigation;
import com.example.wannad.ui.home.HomeDrinks;
import com.example.wannad.ui.review.ReviewFragment;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wannad.MainActivity;
import com.example.wannad.R;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.JsonObject;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import com.example.wannad.ui.map.CategoryResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class MapFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.OpenAPIKeyAuthenticationResultListener, MapView.CurrentLocationEventListener {
    MapView mapView;
    private double currentLon, currentLat;
    // rest api key, 앞에 KakaoAK 꼭 붙여줘야 함
    String key = "KakaoAK a9cbc40f5f92bebee9c7bf20723d3b2a";
    ArrayList<Document> cafeList = new ArrayList<>();
    int check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // 지도 객체 생성
        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 맵 리스너
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setOpenAPIKeyAuthenticationResultListener(this);

        // 맵 리스너를 통해 현재 위치 업데이트
        mapView.setCurrentLocationEventListener(this);
        // 현위치 트랙킹 모드 설정
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        return v;
    }

    private void requestSearchLocal(double x, double y) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        cafeList.clear();

        Call<CategoryResult> call = apiInterface.getSearchCategory(key, "CE7", x + "", y + "", 1000);

        call.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                //Toast.makeText(getActivity(), Boolean.toString(response.isSuccessful()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getDocuments() != null) {
                        cafeList.addAll(response.body().getDocuments());

                        int tagNum = 10;
                        for (Document document : cafeList) {
                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName(document.getPlaceName());
                            Log.d("Place Name", document.getPlaceName());
                            marker.setTag(tagNum++);
                            double x = Double.parseDouble(document.getY());
                            double y = Double.parseDouble(document.getX());
                            //카카오맵은 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
                            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x, y);
                            marker.setMapPoint(mapPoint);
                            marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                            marker.setCustomImageAnchor(0.5f, 1.0f);

                            mapView.addPOIItem(marker);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CategoryResult> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    // 말풍선(POIItem) 클릭시 호출
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        double lat = mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude;
        double lng = mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude;
        String place_name = mapPOIItem.getItemName();

        int index = place_name.indexOf(" ");
        String except_branch_office_name = place_name.substring(0, index);

        if(except_branch_office_name.equals("스타벅스")) {
            Fragment fragment = HomeDrinks.newinstance();
            Bundle bundle = new Bundle(1);
            bundle.putString("name", "STARBUCKS");
            fragment.setArguments(bundle);
            ((BottomNavigation)getActivity()).replaceFragment(fragment);
        }
        if(except_branch_office_name.equals("투썸플레이스")) {
            Fragment fragment = HomeDrinks.newinstance();
            Bundle bundle = new Bundle(1);
            bundle.putString("name", "A TWOSOME PLACE");
            fragment.setArguments(bundle);
            ((BottomNavigation)getActivity()).replaceFragment(fragment);
        }
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    // 현재 위치 업데이트(setCurrentLocationEventListener)
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float accuracyInMeters) {
        if(check == 0)
        {
            mapView.removeAllPOIItems();
            MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
            Log.i("TAG", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
            mapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
            //이 좌표로 지도 중심 이동
            mapView.setMapCenterPoint(mapPoint, true);
            //전역변수로 현재 좌표 저장
            currentLat = mapPointGeo.latitude;
            currentLon = mapPointGeo.longitude;
            Log.d("TAG", "현재위치 => " + currentLat + "  " + currentLon);
            requestSearchLocal(currentLon, currentLat);
            check = 1;
        }
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        Log.i("TAG", "onCurrentLocationUpdateFailed");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        Log.i("TAG", "onCurrentLocationUpdateCancelled");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

}
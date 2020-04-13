package com.example.lee.alarm_application;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * alarm_application
 * Class: mapviewfragement.
 * Created by leedonghun.
 * Created On 2018-08-29.
 * Description:
 */
public class mapviewfragement extends Fragment implements OnMapReadyCallback,  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{


        private static final LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        private static final String TAG = "googlemap_example";
        private static final int GPS_ENABLE_REQUEST_CODE = 2001;
        private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
        private static final int UPDATE_INTERVAL_MS = 15000;
        private static final int FASTEST_UPDATE_INTERVAL_MS = 15000;

        private GoogleMap googleMap = null;
        private MapView mapView = null;
        private GoogleApiClient googleApiClient = null;
        private Marker currentMarker = null;
        private GeofencingClient geofencingClient;
        private  static final int GEOFENCE_RADIUS= 500;
        private  static final int GEOFENCE_EXPIRATION= 6000;
        LatLng currentLocation;



  public interface OnMyListener{
      void onReceivedData(double data);
  }
  public  interface OnMyListner1{
      void onReceiveData1(double data);
  }
 private  OnMyListener onMyListener;
 private  OnMyListner1 onMyListner1;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      onMyListener = (OnMyListener) context;
      onMyListner1=  (OnMyListner1) context;
    }


    //여기서 사용하는 지오코딩은 주소를 지리좌표(위도 및 경도 )등으로 변환하는 프로세스임.
   //이 프로세스를 사용하여 마커를 배ㅣ하거나 지도의 위치를 설정가능함.


    //플레이스오토 프래그먼트 --> 플래이스 오토 컴플릿이
    PlaceAutocompleteFragment autocompleteFragment;


    public mapviewfragement() {
    }


     public  void passData1(double data){
        onMyListner1.onReceiveData1(data);
     }

    public void passData(double data) {
        onMyListener.onReceivedData(data);
    }



   public  void setCurrentLocation(Location location, String markerTitle, String markerSnippet){





        if(location != null){
            currentLocation=new LatLng(location.getLatitude(),location.getLongitude());

            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);

            // 이부분에서  마크 아이콘의 커스톰화를 사용할수 있음.
            //markerOptions.icon()
            currentMarker=this.googleMap.addMarker(markerOptions);

            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
              CircleOptions circleOptions=new CircleOptions()
              .fillColor(0x40ff0000).strokeColor(Color.TRANSPARENT).strokeWidth(2);
              circleOptions.radius(200);
              circleOptions.center(currentLocation);
              Circle circle=googleMap.addCircle(circleOptions);

            //각각의 위도 경도를 double로 받아서 그값을  부모 엑티비티에 interface를 사용하여 넘겨줌.
            double latitude=currentLocation.latitude;
            double longtitude=currentLocation.longitude;
            passData(latitude);
            passData1(longtitude);

            return;
        }



        //default일경우가는 marker
        //MarkerOptions markerOptions=new MarkerOptions();
        //markerOptions.position(DEFAULT_LOCATION);
        //markerOptions.title(markerTitle);
        //markerOptions.snippet(markerSnippet);
        //markerOptions.draggable(true);
        //currentMarker=this.googleMap.addMarker(markerOptions);

        //this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));

   }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }// on create 함수 끝남.





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            View  rootView=inflater.inflate(R.layout.fragment_fragformapview,container,false);
            mapView=(MapView)rootView.findViewById(R.id.mapview);
            mapView.getMapAsync(this);

            //플레이스를
            autocompleteFragment=(PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
              Location location=new Location("");
              location.setLatitude(place.getLatLng().latitude);
              location.setLongitude(place.getLatLng().longitude);
              googleMap.clear();


              setCurrentLocation(location, place.getName().toString(), place.getAddress().toString());


            }

            @Override
            public void onError(Status status) {

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }



    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if(googleApiClient != null && googleApiClient.isConnected())
                googleApiClient.disconnect();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        if(googleApiClient != null)
            googleApiClient.connect();


    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if(googleApiClient != null && googleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
            googleApiClient.disconnect();

        }
    }





    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

        if(googleApiClient != null){
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);

            if(googleApiClient.isConnected()){
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
                googleApiClient.disconnect();
            }


        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapsInitializer.initialize(getActivity().getApplicationContext());
        if(mapView != null){
            mapView.onCreate(savedInstanceState);
        }



    }





    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        setCurrentLocation(null, "위치정보가져올수 없음", "위치 퍼미션과 GPS 활성 여부 확인");

        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onLocationChanged(Location location) {
       // searchCurrentPlaces();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
       // if ( !checkLocationServicesStatus()) {
         //   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
           // builder.setTitle("위치 서비스 비활성화");
           // builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
            //        "위치 설정을 수정하십시오.");
            //builder.setCancelable(true);
            //builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
              //  @Override
                //public void onClick(DialogInterface dialogInterface, int i) {
                  //  Intent callGPSSettingIntent =
                    //        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    //startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
               // }
            //});
           // builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
             //   @Override
               // public void onClick(DialogInterface dialogInterface, int i) {
                 //   dialogInterface.cancel();
               // }
            //});
           // builder.create().show();
       // }

        //LocationRequest locationRequest = new LocationRequest();
        //locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //locationRequest.setInterval(UPDATE_INTERVAL_MS);
        //locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        //if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         //   if ( ActivityCompat.checkSelfPermission(getActivity(),
           //         Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

             // LocationServices.FusedLocationApi
               //         .requestLocationUpdates(googleApiClient, locationRequest, this);
            //}
        //} else {
        //LocationServices.FusedLocationApi
          //          .requestLocationUpdates(googleApiClient, locationRequest, this);

            //this.googleMap.getUiSettings().setCompassEnabled(true);
            //this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //}

    }






    @Override
    public void onConnectionSuspended(int cause) {
        if ( cause ==  CAUSE_NETWORK_LOST )
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED )
            Log.e(TAG,"onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");




    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Location location = new Location("");
        location.setLatitude(DEFAULT_LOCATION.latitude);
        location.setLongitude((DEFAULT_LOCATION.longitude));

        setCurrentLocation(location, "위치정보 가져올 수 없음",
                "위치 퍼미션과 GPS활성 여부 확인");



    }
}

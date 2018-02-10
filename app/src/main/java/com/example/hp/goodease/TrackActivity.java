package com.example.hp.goodease;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class TrackActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
   private Marker currentLocationMarker;
    public static String driver_id;

   // private Marker pickLocationMarker;
   // private Marker dropLocationMarker;
    public static String distance[] = new String[2];
 //   private  List<Address> pickup_address = null;
   // private  List<Address> drop_address = null;
    public static  final  int PERMISSION_REQUEST_LOCATION_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_track2 );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        final Thread t=new Thread(){


            @Override
            public void run(){

                while(!isInterrupted()){

                    try {
                        Thread.sleep(30000);  //1000ms = 1 sec

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Object[] DataTransfer = new Object[3];
                                DataTransfer[0] = "fetch driver location";
                                DataTransfer[1] =  driver_id;
                                DataTransfer[2] = mMap;

                                BWFetchDriverLocation bwFetchDriverLocation = new BWFetchDriverLocation(TrackActivity.this);
                                bwFetchDriverLocation.execute( DataTransfer );
                              //   backgroundWorker.execute(DataTransfer[0],DataTransfer[1],DataTransfer[2]);



                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }

    public void onClick(View v) {
      /*  if (v.getId() == R.id.btn_showmap) {
            EditText Pickup_Location = (EditText) findViewById( R.id.et_pickup );
            EditText Drop_Location = (EditText) findViewById( R.id.et_drop );
            String pickup_location = Pickup_Location.getText().toString();
            String drop_location = Drop_Location.getText().toString();
            Geocoder geocoder = new Geocoder( this );

            MarkerOptions MO_pick = new MarkerOptions();
            MarkerOptions MO_drop = new MarkerOptions();
            if(currentLocationMarker != null)
            {
                currentLocationMarker.remove();
            }
            if(pickLocationMarker != null)
            {
                pickLocationMarker.remove();
            }
            if(dropLocationMarker != null)
            {
                dropLocationMarker.remove();
            }
            if (!pickup_location.equals( "" ) && !drop_location.equals( "" )) {

                try {
                    pickup_address = geocoder.getFromLocationName( pickup_location, 2 );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    drop_address = geocoder.getFromLocationName( drop_location, 2 );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LatLng latLng_pick = new LatLng( pickup_address.get(0).getLatitude(), pickup_address.get(0).getLongitude() );
                LatLng latLng_drop = new LatLng( drop_address.get(0).getLatitude(), drop_address.get(0).getLongitude() );
                MO_pick.position( latLng_pick );
                MO_drop.position( latLng_drop );
                MO_pick.title( "PICKUP" );
                MO_drop.title( "DROP" );
                MO_pick.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                MO_drop.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ROSE ) );
                pickLocationMarker = mMap.addMarker( MO_pick );
                dropLocationMarker = mMap.addMarker( MO_drop );
                mMap.animateCamera( CameraUpdateFactory.newLatLng( latLng_pick ) );

            }
        }*/
       /* if(v.getId() == R.id.btn_continue){
            Log.d("Click","Continue button Clicked" );
            Object dataTransfer[] = new Object[2];
            String url = getDirectionsUrl();
            Log.d("URL", url);
            GetDirectionsData getDirectionsData = new GetDirectionsData();
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            getDirectionsData.execute(dataTransfer);
            Log.d("BT232",distance[0]+distance[1] );
           // startActivity( new Intent( LocationActivity.this , SecondActivity.class ));

        }*/
    }


  /*  private  String getDirectionsUrl()
    {
        StringBuilder googleDirectionsUrl = new StringBuilder( "https://maps.googleapis.com/maps/api/directions/json?" );
        googleDirectionsUrl.append("origin="+pickup_address.get(0).getLatitude()+","+pickup_address.get(0).getLongitude());
        googleDirectionsUrl.append("&destination="+drop_address.get(0).getLatitude()+","+drop_address.get(0).getLongitude());
        googleDirectionsUrl.append( "&key="+"AIzaSyAl4msqOCbh4NR1pEZtGPI-DLx-gSGgeic" );

        return googleDirectionsUrl.toString();

    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case  PERMISSION_REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                }
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }

    }

    protected synchronized  void buildGoogleApiClient()
    {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Pickup Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED )

            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);

    }
    public  boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

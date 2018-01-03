package com.example.hp.goodease;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by hp on 12/16/2017.
 */

public class GetDirectionsData extends AsyncTask<Object,Void,String> {
    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    @Override
    protected String doInBackground(Object... params) {

        mMap = (GoogleMap)params[0];
        url = (String)params[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        Log.d("URL", "Download url formed");
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
            Log.d("GDD", googleDirectionsData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;


    }

    @Override
    protected void onPostExecute(String s)
    {
        Log.d( "on post ex", "dp formed" );
        HashMap<String, String> abc = null;
        DataParcel parcel = new DataParcel();

        abc = parcel.parseDirections( s );

        duration = abc.get( "duration" );
        distance = abc.get( "distance" );


        SecondActivity.dur_dis[0]=distance;
        SecondActivity.dur_dis[1]=duration;

        Log.d( "BT ", SecondActivity.dur_dis[0]+SecondActivity.dur_dis[1] );
    }
}

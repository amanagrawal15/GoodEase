package com.example.hp.goodease;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ProgrammingKnowledge on 1/5/2016.
 */
public class BWFetchDriverLocation extends AsyncTask<Object,Void,String> {
    GoogleMap mMap;
    public Context context;
    AlertDialog alertDialog;
    public BWFetchDriverLocation(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(Object... params) {
        String type = (String)params[0];
        mMap = (GoogleMap)params[2];
        String fetch_driver_location_url = "http://goodease.000webhostapp.com/fetch_driver_location.php";

         if(type.equals("fetch driver location")) {
            try {
                String Driver_ID = (String)params[1];
                URL url = new URL(fetch_driver_location_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("driver_id","UTF-8")+"="+URLEncoder.encode(Driver_ID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        // alertDialog = new AlertDialog.Builder(context).create();
        // alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        Double lat = Double.parseDouble( result.substring( 0,result.indexOf( ',' ) ));
        Double lon = Double.parseDouble( result.substring( (result.indexOf( ',' )+1) ) );
        LatLng latLng_pick = new LatLng( lat,lon );
        Marker Driver_Current_Location = null;
        MarkerOptions MO_driver = new MarkerOptions();
        if(Driver_Current_Location!=null){
            Driver_Current_Location.remove();
        }
        MO_driver.position( latLng_pick );
        MO_driver.title( "Your Driver is here" );
        MO_driver.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
        Driver_Current_Location = mMap.addMarker( MO_driver );
        mMap.animateCamera( CameraUpdateFactory.newLatLng( latLng_pick ) );

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}






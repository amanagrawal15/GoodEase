package com.example.hp.goodease;

import android.content.Intent;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

//import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by ProgrammingKnowledge on 1/5/2016.
 */
public class BWPlaceOrder extends AsyncTask<String,Void,String> {
    public Context context;
    AlertDialog alertDialog;
    public BWPlaceOrder (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String placeOrder_url = "http://goodease.000webhostapp.com/nearest_driver.php";
         if(type.equals("place order")) {
            try {
                String User_ID = params[1];
                String Pickup_latitude = params[2];
                String Pickup_longitude = params[3];
                String Drop_latitude = params[4];
                String Drop_longitude = params[5];
                String price = params[6];
                URL url = new URL(placeOrder_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(User_ID,"UTF-8")+"&"
                        +URLEncoder.encode("pick_latitude","UTF-8")+"="+URLEncoder.encode(Pickup_latitude,"UTF-8")+"&"+
                        URLEncoder.encode( "pick_longitude","UTF-8" )+"="+URLEncoder.encode( Pickup_longitude,"UTF-8" )+"&"+
                        URLEncoder.encode( "drop_latitude","UTF-8" )+"="+URLEncoder.encode( Drop_latitude,"UTF-8" )+"&"+
                        URLEncoder.encode( "drop_longitude","UTF-8" )+"="+URLEncoder.encode( Drop_longitude,"UTF-8" )+"&"+
                        URLEncoder.encode("estimated_price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8");
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
        TrackActivity.driver_id = result;
        Toast.makeText( context, result, Toast.LENGTH_SHORT ).show();
        Intent intent = new Intent( context, TrackActivity.class );
        context.startActivity(intent);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}




package com.example.hp.goodease;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hp on 12/15/2017.
 */

public class DataParcel {
    private  HashMap<String,String> getDuration(JSONArray googleDirectionsJson)
    {
        HashMap<String,String> googleDirectionsMap = new HashMap<>(  );
        String duration = null;
        String distance = null;
        try {
            duration = googleDirectionsJson.getJSONObject( 0 ).getJSONObject( "duration" ).getString( "text" );
            distance = googleDirectionsJson.getJSONObject( 0 ).getJSONObject( "distance" ).getString( "text" );

            googleDirectionsMap.put( "duration", duration );
            googleDirectionsMap.put("distance", distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  googleDirectionsMap;

    }






   private HashMap<String, String> getPlace(JSONObject googlePlaceJson){
    HashMap<String, String> googlePlacesMap = new HashMap<>(  );
    String placeName = "-NA-";
    String vicinity = "-NA-";
    String latitude = "";
    String longitude = "";
    String reference = "";

    try {
    if(!googlePlaceJson.isNull( "name" ))
    {

            placeName = googlePlaceJson.getString( "name" );
        }
        if(!googlePlaceJson.isNull( "vicinity" ))
        {
            vicinity = googlePlaceJson.getString( "vicinity" );
        }
        latitude = googlePlaceJson.getJSONObject( "geometry" ).getJSONObject( "location" ).getString( "lat" );
        longitude = googlePlaceJson.getJSONObject( "geometry" ).getJSONObject( "location" ).getString( "lng" );
        reference = googlePlaceJson.getString( "reference" );
        googlePlacesMap.put("place_name", placeName);
        googlePlacesMap.put("vicinity", vicinity);
        googlePlacesMap.put("latitude", latitude);
        googlePlacesMap.put("longitude", longitude);
        googlePlacesMap.put("reference", reference);
    }
        catch (JSONException e) {
            e.printStackTrace();
        }

    return googlePlacesMap;
}
private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
{
    int count = jsonArray.length();
    List<HashMap<String,String>> placesList = new ArrayList<>(  );
    HashMap<String,String> placeMap = null;

    for(int i=0 ;i<count;i++)
    {
        try {
            placeMap = getPlace((JSONObject) jsonArray.get(i) );
            placesList.add(placeMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    return placesList;
}

 public List<HashMap<String,String>> parse(String jsonData)
 {
     JSONArray jsonArray = null;
     try {
         JSONObject jsonObject = new JSONObject( jsonData );
         jsonArray = jsonObject.getJSONArray( "results" );
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return  getPlaces( jsonArray );
 }
 public  HashMap<String,String> parseDirections(String jsonData)
 {
     Log.d( "parse dir", "etpd" );
     JSONArray jsonArray = null;
     JSONObject jsonObject;


     try {
          jsonObject = new JSONObject( jsonData );
         Log.d( "json bkl", "jsonobj formed" );
         jsonArray = jsonObject.getJSONArray( "routes" ).getJSONObject( 0 ).getJSONArray( "legs" );
     } catch (JSONException e) {
         e.printStackTrace();
     }
     Log.d( "JSONArray", jsonArray.toString() );
     return  getDuration(jsonArray);


 }
}

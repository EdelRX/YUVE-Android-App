package com.edeldoe.yuveapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EdelRX on 24/04/2016.
 */

    class DownloadCinemaMap extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... params) {

            String mapquery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + MapFragment.latitude + "," + MapFragment.longitude + "&rankby=distance&types=movie_theater&key=AIzaSyAc-k92KsbO4Ii1ID0hBN9WeDGwFSc1Bpo";

            try {
                URL url = new URL(mapquery); // Construct url object
                HttpURLConnection conn1 = (HttpURLConnection) url.openConnection(); // Establish connection with url
                conn1.setReadTimeout(10000 /* milliseconds */); // Set read timeout
                conn1.setConnectTimeout(15000 /* milliseconds */); // Set connection timeout
                conn1.setRequestMethod("GET"); // Set request method
                conn1.setDoInput(true);
                // Starts the query
                conn1.connect(); // Establish connection

                try {
                    InputStream is = conn1.getInputStream(); // Download JSON stream
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is)); // Construct downloader
                    StringBuilder stringBuilder = new StringBuilder(); // Save stream in stringbuilder
                    String line;
                    while ((line = bufferedReader.readLine()) != null) { //Append line
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close(); // Close downloader
                    return stringBuilder.toString(); // Convert the download stream to a string and return it
                } finally {
                    //conn1.disconnect();
                }

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String result) {
            Log.d("RESULT IS", result);
            if (yuve_maps.placeMarkers != null) {
                for (int pm = 0; pm < yuve_maps.placeMarkers.length; pm++) {
                    if (yuve_maps.placeMarkers[pm] != null)
                        yuve_maps.placeMarkers[pm].remove();
                }
            }
            MarkerOptions[] places = null;
            try {
                JSONObject resultObject = new JSONObject(result);
                JSONArray placesArray = resultObject.getJSONArray("results");
                places = new MarkerOptions[placesArray.length()];
                //loop through places
                for (int p = 0; p < placesArray.length(); p++) {
                    boolean missingValue = false;
                    LatLng placeLL = null;
                    String placeName = "";
                    String vicinity = "";
                    try {
                        missingValue = false;
                        JSONObject placeObject = placesArray.getJSONObject(p);
                        JSONObject loc = placeObject.getJSONObject("geometry").getJSONObject("location");
                        placeLL = new LatLng(
                                Double.valueOf(loc.getString("lat")),
                                Double.valueOf(loc.getString("lng")));
                        vicinity = placeObject.getString("vicinity");
                        placeName = placeObject.getString("name");

                    } catch (JSONException jse) {
                        missingValue = true;
                        jse.printStackTrace();
                    }
                    if (missingValue) {
                        places[p] = null;
                    } else {

                        places[p] = new MarkerOptions()
                                .position(placeLL)
                                .title(placeName)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .snippet(vicinity);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }



            if (places != null && yuve_maps.placeMarkers != null) {
                for (int p = 0; p < places.length && p < yuve_maps.placeMarkers.length; p++) {
                    //will be null if a value was missing
                    if (places[p] != null)
                        yuve_maps.placeMarkers[p] = MapFragment.map.addMarker(places[p]);
                }
            }
        }

    }


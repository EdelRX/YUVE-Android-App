package com.edeldoe.yuveapp;

// Uses AsyncTask to create a task away from the main UI thread. This task takes a
// URL string and uses it to create an HttpUrlConnection. Once the connection
// has been established, the AsyncTask downloads the contents of the webpage as
// an InputStream. Finally, the InputStream is converted into a string, which is
// displayed in the UI by the AsyncTask's onPostExecute method.



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.GridView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class DownloadTrailer extends AsyncTask<String, String, String>{

    String movie_id;

    // Not used for now
    @Override
    public void onPreExecute() {
        movie_id = yuve_movie.setid;
    }
    //

    // This method will download info from a url, then parse, then download the posters
    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.


        try {


            ParseData parsedata = new ParseData(); // Create instance of class to call its methods
            String parseinput = downloadUrl(); // Obtain JSON string from API

            Log.d("TRAILER query", parseinput);

            String trailerurl = parsedata.readJsonTrailer(parseinput); // Calling method to obtain desired info
            Log.d("URL", trailerurl);
            yuve_movie.trailer = trailerurl;
            return trailerurl;
        } catch (IOException e) {

            Log.e("ERROR", e.getMessage(), e);
            return "Error";
        }


    }


    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl() throws IOException {


        String trailerquery = yuve_start.ctx.getResources().getString(R.string.MovieURL)+movie_id+yuve_start.ctx.getResources().getString(R.string.TrailerURL); // Query url to download JSON info

        try {
            URL url = new URL(trailerquery); // Construct url object
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
            }
            finally {
                //conn1.disconnect();
            }

        }

        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    // After download all images, insert them into the grid.
    public void onPostExecute(String result) {

    }


}


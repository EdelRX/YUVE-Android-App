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
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class DownloadSearchResults extends AsyncTask<String, String, String>{

    public static ArrayList<ImageItem> imageItems2 = new ArrayList<>(); //An array list to store images
    public static GridView gridView; // Object to find the gridview xml item
    public static GridViewAdapter gridAdapter; //Adaptar to add images to the grid
    public static List posterlist2;
    public static List titlelist2;
    public static List overviewlist2;
    public static List idlist2;
    public static List ratings;
    public static List genre;
    public static List releasedates;
    public static List popularity;

    // Not used for now
    @Override
    public void onPreExecute() {


    }
    //

    // This method will download info from a url, then parse, then download the posters
    @Override
    protected String doInBackground(String... search) {
        // params comes from the execute() call: params[0] is the url.
        imageItems2.clear();
        if(imageItems2.size() == 0) { // If we have no stored images
            BitmapFactory.Options opt = new BitmapFactory.Options(); // Size configuration for the downloaded images
            opt.inSampleSize = 4; // Scaled 1/4
            try {

                ParseData parsedata = new ParseData(); // Create instance of class to call its methods
                String parseinput = downloadUrl(search[0]); // Obtain JSON string from API

                Log.d("POSTER", parseinput);

                posterlist2 = parsedata.readJsonStream(parseinput,1); // Calling method to obtain desired info
                titlelist2 = parsedata.readJsonStream(parseinput,2); // Calling method to obtain desired info
                overviewlist2 = parsedata.readJsonStream(parseinput,3); // Calling method to obtain desired info
                idlist2 = parsedata.readJsonStream(parseinput, 4);
                ratings = parsedata.readJsonStream(parseinput,5);
                genre = parsedata.readJsonStream(parseinput,6);
                releasedates = parsedata.readJsonStream(parseinput,7);
                popularity = parsedata.readJsonStream(parseinput, 8);

                Log.d("POSTER", posterlist2.toString());

                String baseurl = yuve_start.ctx.getResources().getString(R.string.PosterPath); // Store a base url to append the poster paths

                //Log.d("POSTER",posterurl.toString());

                for (int i = 0; i < posterlist2.size(); i++) { // Travel poster url list

                    String path = (String) posterlist2.get(i); // Obtain url at this position

                    if (!path.equals("null")) { // If there is a url (sometimes there isn't)

                        String posterurl = baseurl + path; // Construct full url to fetch data
                        Log.d("POSTER", posterurl.toString());

                        URL url = new URL(posterurl); // Call java method to build url object
                        HttpURLConnection conn2 = (HttpURLConnection) url.openConnection(); // Use url to connect
                        conn2.setReadTimeout(10000 /* milliseconds */); // Set max read timeout
                        conn2.setConnectTimeout(15000 /* milliseconds */); // Set max connect timeout
                        conn2.setRequestMethod("GET"); // Set request method
                        conn2.setDoInput(true);
                        // Starts the query
                        conn2.connect(); // Initialize connect

                        try {
                            InputStream is = conn2.getInputStream(); // Download poster

                            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt); // Decode poster into bitmap
                            imageItems2.add(new ImageItem(bitmap)); // Add this downloaded poster to the image list

                        } finally {
                            //conn2.disconnect();
                        }
                    }
                    else{
                        Bitmap bitmap = BitmapFactory.decodeResource(yuve_search_results.activity.getResources(), R.drawable.posternotavailable2);
                        imageItems2.add(new ImageItem(bitmap));
                    }

                }


                return "Success";
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage(), e);
                return "Error";
            }
        }

        return "List is full";
    }


    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String q) throws IOException {

        q = q.replaceAll("\\s", "\\+");
        String search_query = yuve_start.ctx.getResources().getString(R.string.SearchURL)+q; // Query url to download JSON info
        Log.d("URL DE BUSQUEDA", search_query);
        try {
            URL url = new URL(search_query); // Construct url object
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
                Log.d("STRING STREAM", stringBuilder.toString());
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

        yuve_search_results.activity.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

        gridView = (GridView) yuve_search_results.activity.findViewById(R.id.gridView); // Buscamos la lista por ID y la guardamos aqui
        gridAdapter = new GridViewAdapter(yuve_search_results.activity, R.layout.grid_item_layout, imageItems2); //Creamos un adaptador y le metemos las imagenes obtenidas en getData()
        gridView.setAdapter(gridAdapter); //Le asignamos el adaptador a la lista para que se quede las imagenes que ahora tiene el adaptador

        if(imageItems2.isEmpty()){
            Toast.makeText(yuve_search_results.activity, "No results available.", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(yuve_search_results.activity, "Long press for movie title. Tap for details.", Toast.LENGTH_LONG).show();

        }
    }



}


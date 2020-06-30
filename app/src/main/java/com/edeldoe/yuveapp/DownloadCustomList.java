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

class DownloadCustomList extends AsyncTask<String, String, String>{

    public static ArrayList<ImageItem> imageItems6 = new ArrayList<>(); //An array list to store images
    public static GridView gridView; // Object to find the gridview xml item
    public static GridViewAdapter gridAdapter; //Adaptar to add images to the grid
    public static List titlelist6 = new ArrayList<String>();
    public static List overviewlist6 = new ArrayList<String>();
    public static List idlist6 = new ArrayList<String>();
    public static List ratings3= new ArrayList<String>();
    public static List genre3= new ArrayList<String>();
    public static List releasedates3= new ArrayList<String>();
    public static List popularity3= new ArrayList<String>();

    // Not used for now
    @Override
    public void onPreExecute() {

    }
    //

    // This method will download info from a url, then parse, then download the posters
    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.

        if(imageItems6.size() == 0) { // If we have no stored images
            BitmapFactory.Options opt = new BitmapFactory.Options(); // Size configuration for the downloaded images
            opt.inSampleSize = 4; // Scaled 1/4
            try {
                List posterlist = new ArrayList<String>();
                titlelist6 = new ArrayList<String>();
                overviewlist6 = new ArrayList<String>();
                idlist6 = new ArrayList<String>();
                ratings3= new ArrayList<String>();
                genre3= new ArrayList<String>();
                releasedates3= new ArrayList<String>();
                popularity3= new ArrayList<String>();
                ParseData parsedata = new ParseData(); // Create instance of class to call its methods
                String movie;
                String mq;
                String parseinput;
                if(!((yuve_start.myPref.getString("customlist","noids")).equals("noids"))){

                    for (int a = 0; a < yuve_start.customlist.size(); a++) {
                        movie = yuve_start.customlist.get(a);
                        mq = yuve_start.ctx.getResources().getString(R.string.MovieURL) + movie +"?api_key="+ yuve_start.ctx.getResources().getString(R.string.APIKey); // Query url to download JSON info
                        // Obtain JSON string from API
                        parseinput = downloadUrl(mq);
                        Log.d("PARSEINPUT", parseinput);

                        posterlist.add(parsedata.readResult(parseinput, 1)); // Calling method to obtain desired info
                        titlelist6.add(parsedata.readResult(parseinput, 2)); // Calling method to obtain desired info
                        overviewlist6.add(parsedata.readResult(parseinput, 3)); // Calling method to obtain desired info
                        idlist6.add(parsedata.readResult(parseinput, 4));  // Calling method to obtain desired info
                        ratings3.add(parsedata.readResult(parseinput, 6));
                        genre3.add(parsedata.getGenreId(parseinput));
                        Log.d("GENRE", genre3.toString());
                        releasedates3.add(parsedata.readResult(parseinput, 8));
                        popularity3.add(parsedata.readResult(parseinput, 9));
                        Log.d("POSTER", posterlist.get(a).toString());
                        Log.d("TITLE", titlelist6.get(a).toString());
                    }
                }

                String baseurl = yuve_start.ctx.getString((R.string.PosterPath)); // Store a base url to append the poster paths

                //Log.d("POSTER",posterurl.toString());

                for (int i = 0; i < posterlist.size(); i++) { // Travel poster url list

                    String path = (String) posterlist.get(i); // Obtain url at this position
                    Log.d("PATH", path);

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
                            imageItems6.add(new ImageItem(bitmap)); // Add this downloaded poster to the image list

                        } finally {
                            //conn2.disconnect();
                        }
                    }else{
                        Bitmap bitmap = BitmapFactory.decodeResource(yuve_favorites.activity.getResources(), R.drawable.posternotavailable2);
                        imageItems6.add(new ImageItem(bitmap));
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
    private String downloadUrl(String moviequery) throws IOException {


        try {
            URL url = new URL(moviequery); // Construct url object
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

    // After download all images, insert them into the grid.
    public void onPostExecute(String result) {

        yuve_custom.activity.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        gridView = (GridView) yuve_custom.activity.findViewById(R.id.gridView); // Buscamos la lista por ID y la guardamos aqui
        gridAdapter = new GridViewAdapter(yuve_custom.activity, R.layout.grid_item_layout, imageItems6); //Creamos un adaptador y le metemos las imagenes obtenidas en getData()
        gridView.setAdapter(gridAdapter); //Le asignamos el adaptador a la lista para que se quede las imagenes que ahora tiene el adaptador
        gridView.setVisibility(View.VISIBLE);
        if((yuve_start.myPref.getString("customlist","noids")).equals("noids")) {
            Toast.makeText(yuve_custom.activity, "Your list is empty", Toast.LENGTH_LONG).show();
        }

        if(imageItems6.isEmpty()){
            yuve_custom.clear_fav_button.setVisibility(View.INVISIBLE);
        }else{
            yuve_custom.clear_fav_button.setVisibility(View.VISIBLE);
        }
    }


}

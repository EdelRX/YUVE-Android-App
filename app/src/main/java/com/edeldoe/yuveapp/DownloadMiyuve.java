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

class DownloadMiyuve extends AsyncTask<String, String, String>{

    public static ArrayList<ImageItem> imageItems4 = new ArrayList<>(); //An array list to store images
    public static ArrayList<ImageItem> imageItems5 = new ArrayList<>(); //An array list to store images
    public static ArrayList<ImageItem> imageItems6 = new ArrayList<>(); //An array list to store images

    public static GridView gridView; // Object to find the gridview xml item
    public static GridViewAdapter gridAdapter; //Adaptar to add images to the grid
    public static GridView gridView2; // Object to find the gridview xml item
    public static GridViewAdapter gridAdapter2; //Adaptar to add images to the grid
    public static GridView gridView3; // Object to find the gridview xml item
    public static GridViewAdapter gridAdapter3; //Adaptar to add images to the grid

    public static List titlelist4 = new ArrayList<String>();
    public static List overviewlist4 = new ArrayList<String>();
    public static List idlist4 = new ArrayList<String>();
    public static List ratings= new ArrayList<String>();
    public static List genre= new ArrayList<String>();
    public static List releasedates= new ArrayList<String>();
    public static List popularity= new ArrayList<String>();

    public static List titlelist5 = new ArrayList<String>();
    public static List overviewlist5 = new ArrayList<String>();
    public static List idlist5 = new ArrayList<String>();
    public static List ratings2= new ArrayList<String>();
    public static List genre2= new ArrayList<String>();
    public static List releasedates2= new ArrayList<String>();
    public static List popularity2= new ArrayList<String>();

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

        if(imageItems4.size() == 0) { // If we have no stored images
            BitmapFactory.Options opt = new BitmapFactory.Options(); // Size configuration for the downloaded images
            opt.inSampleSize = 4; // Scaled 1/4
            try {
                List posterlist = new ArrayList<String>();
                titlelist4 = new ArrayList<String>();
                overviewlist4 = new ArrayList<String>();
                idlist4 = new ArrayList<String>();
                ratings= new ArrayList<String>();
                genre= new ArrayList<String>();
                releasedates= new ArrayList<String>();
                popularity= new ArrayList<String>();
                ParseData parsedata = new ParseData(); // Create instance of class to call its methods
                String movie;
                String mq;
                String parseinput;
                if(!((yuve_start.myPref.getString("favorites","noids")).equals("noids"))){

                    for (int a = 0; a < yuve_start.favorites.size(); a++) {
                        movie = yuve_start.favorites.get(a);
                        mq = "http://api.themoviedb.org/3/movie/" + movie + "?api_key=36e0ece2f7eee65440f0b495a712456d"; // Query url to download JSON info
                        // Obtain JSON string from API
                        parseinput = downloadUrl(mq);
                        Log.d("PARSEINPUT", parseinput);

                        posterlist.add(parsedata.readResult(parseinput, 1)); // Calling method to obtain desired info
                        titlelist4.add(parsedata.readResult(parseinput, 2)); // Calling method to obtain desired info
                        overviewlist4.add(parsedata.readResult(parseinput, 3)); // Calling method to obtain desired info
                        idlist4.add(parsedata.readResult(parseinput, 4));  // Calling method to obtain desired info
                        ratings.add(parsedata.readResult(parseinput, 6));
                        genre.add(parsedata.getGenreId(parseinput));
                        Log.d("GENRE", genre.toString());
                        releasedates.add(parsedata.readResult(parseinput, 8));
                        popularity.add(parsedata.readResult(parseinput, 9));
                        Log.d("POSTER", posterlist.get(a).toString());
                        Log.d("TITLE", titlelist4.get(a).toString());
                    }
                }

                String baseurl = "http://image.tmdb.org/t/p/w500"; // Store a base url to append the poster paths

                //Log.d("POSTER",posterurl.toString());

                for (int i = 0; i < posterlist.size(); i++) { // Travel poster url list
                    if(i == 3) {
                        break;
                    }

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

                            imageItems4.add(new ImageItem(bitmap)); // Add this downloaded poster to the image list



                        } finally {
                            //conn2.disconnect();
                        }
                    }else{
                        Bitmap bitmap = BitmapFactory.decodeResource(miyuve.activity.getResources(), R.drawable.posternotavailable2);
                        imageItems4.add(new ImageItem(bitmap));
                    }

                }


            } catch (IOException e) {
                Log.e("ERROR", e.getMessage(), e);
                return "Error";
            }
        }


        if(imageItems5.size() == 0) { // If we have no stored images
            BitmapFactory.Options opt = new BitmapFactory.Options(); // Size configuration for the downloaded images
            opt.inSampleSize = 4; // Scaled 1/4
            try {
                List posterlist = new ArrayList<String>();
                titlelist5 = new ArrayList<String>();
                overviewlist5 = new ArrayList<String>();
                idlist5 = new ArrayList<String>();
                ratings2= new ArrayList<String>();
                genre2= new ArrayList<String>();
                releasedates2= new ArrayList<String>();
                popularity2= new ArrayList<String>();
                ParseData parsedata = new ParseData(); // Create instance of class to call its methods
                String movie;
                String mq;
                String parseinput;

                if(!((yuve_start.myPref.getString("watchlist","noids")).equals("noids"))){

                    for (int a = 0; a < yuve_start.watchlist.size(); a++) {
                        movie = yuve_start.watchlist.get(a);
                        mq = "http://api.themoviedb.org/3/movie/" + movie + "?api_key=36e0ece2f7eee65440f0b495a712456d"; // Query url to download JSON info
                        // Obtain JSON string from API
                        parseinput = downloadUrl(mq);
                        Log.d("PARSEINPUT", parseinput);

                        posterlist.add(parsedata.readResult(parseinput, 1)); // Calling method to obtain desired info
                        titlelist5.add(parsedata.readResult(parseinput, 2)); // Calling method to obtain desired info
                        overviewlist5.add(parsedata.readResult(parseinput, 3)); // Calling method to obtain desired info
                        idlist5.add(parsedata.readResult(parseinput, 4));  // Calling method to obtain desired info
                        ratings2.add(parsedata.readResult(parseinput, 6));
                        genre2.add(parsedata.getGenreId(parseinput));
                        Log.d("GENRE", genre.toString());
                        releasedates2.add(parsedata.readResult(parseinput, 8));
                        popularity2.add(parsedata.readResult(parseinput, 9));
                        Log.d("POSTER", posterlist.get(a).toString());
                        Log.d("TITLE", titlelist5.get(a).toString());
                    }
                }

                String baseurl = "http://image.tmdb.org/t/p/w500"; // Store a base url to append the poster paths

                //Log.d("POSTER",posterurl.toString());

                for (int i = 0; i < posterlist.size(); i++) { // Travel poster url list
                    if(i == 3) {
                        break;
                    }
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

                            imageItems5.add(new ImageItem(bitmap)); // Add this downloaded poster to the image list

                        } finally {
                            //conn2.disconnect();
                        }
                    }else{
                        Bitmap bitmap = BitmapFactory.decodeResource(miyuve.activity.getResources(), R.drawable.posternotavailable2);
                        imageItems5.add(new ImageItem(bitmap));
                    }

                }


            } catch (IOException e) {
                Log.e("ERROR", e.getMessage(), e);
                return "Error";
            }
        }

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
                        mq = "http://api.themoviedb.org/3/movie/" + movie + "?api_key=36e0ece2f7eee65440f0b495a712456d"; // Query url to download JSON info
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

                String baseurl = "http://image.tmdb.org/t/p/w500"; // Store a base url to append the poster paths

                //Log.d("POSTER",posterurl.toString());

                for (int i = 0; i < posterlist.size(); i++) { // Travel poster url list
                    if(i == 3) {
                        break;
                    }

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

        if((yuve_start.myPref.getString("favorites","noids")).equals("noids")){
            Bitmap bitmap = BitmapFactory.decodeResource(miyuve.activity.getResources(), R.drawable.emptylist);
            DownloadMiyuve.imageItems4 = new ArrayList<>();
            imageItems4.add(new ImageItem(bitmap));
        }

        if((yuve_start.myPref.getString("watchlist","noids")).equals("noids")){
            Bitmap bitmap = BitmapFactory.decodeResource(miyuve.activity.getResources(), R.drawable.emptylist);
            DownloadMiyuve.imageItems5 = new ArrayList<>();
            imageItems5.add(new ImageItem(bitmap));
        }

        if((yuve_start.myPref.getString("customlist","noids")).equals("noids")){
            Bitmap bitmap = BitmapFactory.decodeResource(miyuve.activity.getResources(), R.drawable.emptylist);
            DownloadMiyuve.imageItems6 = new ArrayList<>();
            imageItems6.add(new ImageItem(bitmap));
        }


        miyuve.activity.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        gridView = (GridView) miyuve.activity.findViewById(R.id.favorites_grid); // Buscamos la lista por ID y la guardamos aqui
        gridAdapter = new GridViewAdapter(miyuve.activity, R.layout.miyuve_griditem_layout, imageItems4); //Creamos un adaptador y le metemos las imagenes obtenidas en getData()
        gridView.setAdapter(gridAdapter); //Le asignamos el adaptador a la lista para que se quede las imagenes que ahora tiene el adaptador
        gridView.setVisibility(View.VISIBLE);

        gridView2 = (GridView) miyuve.activity.findViewById(R.id.watchlist_grid); // Buscamos la lista por ID y la guardamos aqui
        gridAdapter2 = new GridViewAdapter(miyuve.activity, R.layout.miyuve_griditem_layout, imageItems5); //Creamos un adaptador y le metemos las imagenes obtenidas en getData()
        gridView2.setAdapter(gridAdapter2); //Le asignamos el adaptador a la lista para que se quede las imagenes que ahora tiene el adaptador
        gridView2.setVisibility(View.VISIBLE);

        gridView3 = (GridView) miyuve.activity.findViewById(R.id.lista_personalizada); // Buscamos la lista por ID y la guardamos aqui
        gridAdapter3 = new GridViewAdapter(miyuve.activity, R.layout.miyuve_griditem_layout, imageItems6); //Creamos un adaptador y le metemos las imagenes obtenidas en getData()
        gridView3.setAdapter(gridAdapter3); //Le asignamos el adaptador a la lista para que se quede las imagenes que ahora tiene el adaptador
        gridView3.setVisibility(View.VISIBLE);
    }


}


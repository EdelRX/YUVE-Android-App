package com.edeldoe.yuveapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;


public class yuve_start extends Activity {
    public static String[] movie_ids;
    public static SharedPreferences myPref;

    public static String watchlist_string;
    public static String favorites_string;
    public static String custom_string;

    public static List<String> favorites = new ArrayList<String>();
    public static List<String> watchlist = new ArrayList<String>();
    public static List<String> customlist = new ArrayList<String>();


    public static int index;

    public static String preceding;
    public static Boolean watchmodify = false;
    public static Boolean favmodify = false;
    public static Boolean custommodify = false;
    public static Boolean watchmodify2 = false;
    public static Boolean favmodify2 = false;
    public static Boolean custommodify2 = false;
    public static Context ctx;
    public static String custom_title = "Custom list";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        //Action bar code: remove
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int mode = Context.MODE_PRIVATE;
        myPref = getSharedPreferences("com.edeldoe.yuveapp.MY_PREFS", mode);
        loadWatchlist();
        loadFavorites();
        loadCustom();

        setContentView(R.layout.activity_yuve_start);
        //End action bar code: remove




    }
    //Screen tap method
    public void screenTapped(View view){

        Intent intent = new Intent(yuve_start.this, miyuve.class);
        startActivity(intent);

    }
    //End screen tap method

    //Load lists
    public void loadWatchlist(){
        String w = myPref.getString("watchlist","noids");
        movie_ids = w.split(",");


        for (int i = 0; i < movie_ids.length; i++) {
            watchlist.add(movie_ids[i]); //lista

            //  watchlist_string.append(movie_ids[i]); //String builder
            //  watchlist_string.append(",");
        }


        String t = watchlist.toString();
        Log.d("LOADED WATCHLIST:", w);
        int i = yuve_start.watchlist.size();                           // ver el tamaño de la lista
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        String i_string = sb.toString();
        Log.d("TAMAÑO DE LA LISTA", i_string);
    }
    public void loadFavorites(){
        String w = myPref.getString("favorites","noids");
        movie_ids = w.split(",");


        for (int i = 0; i < movie_ids.length; i++) {
            favorites.add(movie_ids[i]); //lista

            //  watchlist_string.append(movie_ids[i]); //String builder
            //  watchlist_string.append(",");
        }


        String t = favorites.toString();
        Log.d("LOADED FAVORITES:", w);
        int i = yuve_start.favorites.size();                           // ver el tamaño de la lista
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        String i_string = sb.toString();
        Log.d("TAMAÑO DE LA LISTA", i_string);
    }

    public void loadCustom(){
        String w = myPref.getString("customlist","noids");
        String savedtitle = myPref.getString("customtitle","Custom list");
        custom_title = savedtitle;
        movie_ids = w.split(",");


        for (int i = 0; i < movie_ids.length; i++) {
            customlist.add(movie_ids[i]); //lista

            //  watchlist_string.append(movie_ids[i]); //String builder
            //  watchlist_string.append(",");
        }


        String t = customlist.toString();
        Log.d("LOADED CUSTOMLIST:", w);
        int i = yuve_start.customlist.size();                           // ver el tamaño de la lista
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        String i_string = sb.toString();
        Log.d("TAMAÑO DE LA LISTA", i_string);
    }
}

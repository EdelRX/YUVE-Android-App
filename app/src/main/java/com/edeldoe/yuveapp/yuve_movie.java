package com.edeldoe.yuveapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class yuve_movie extends Activity {

    private ListView list;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> listadapter;
    public EditText text_search;
    public static String setid;
    public static String trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Action bar code
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar);
        TextView mTextView = (TextView) findViewById(R.id.title);

        setContentView(R.layout.activity_yuve_movie);
        getActionBar().setIcon(new ColorDrawable((getResources().getColor(android.R.color.transparent))));
        //End action bar code

        TextView movie_title = (TextView) findViewById(R.id.titulo_pelicula);
        ImageView movie_poster = (ImageView) findViewById(R.id.poster);
        TextView sinopsis = (TextView) findViewById(R.id.textView);
        TextView rating = (TextView) findViewById(R.id.ratings);
        TextView genre = (TextView) findViewById(R.id.genre);
        TextView release = (TextView) findViewById(R.id.release_date);
        TextView popularity = (TextView) findViewById(R.id.popularity);
        String settitle = "";

        if(yuve_start.preceding.equals("opening")) {
            DownloadOpening downloadopening = new DownloadOpening();

            settitle = (String) downloadopening.titlelist.get(yuve_start.index);
            movie_title.setText(settitle);

            movie_poster.setImageBitmap(downloadopening.imageItems.get(yuve_start.index).getImage());


            String setsinopsis = (String) downloadopening.overviewlist.get(yuve_start.index);
            setid = (String) downloadopening.idlist.get(yuve_start.index);
            sinopsis.setText(setsinopsis);


            String srating = (String) downloadopening.ratings.get(yuve_start.index);
            String sgenre = (String) downloadopening.genre.get(yuve_start.index);
            String srelease = (String) downloadopening.releasedates.get(yuve_start.index);
            String spopularity = (String) downloadopening.popularity.get(yuve_start.index);

            rating.setText("Rating: "+srating);
            sgenre = new ParseData().getGenre(sgenre);
            genre.setText("Genre: "+sgenre);
            release.setText("Release Date: "+ srelease);
            popularity.setText("Popularity: " + spopularity);



        }else if(yuve_start.preceding.equals("search")){
            DownloadSearchResults downloadsearch = new DownloadSearchResults();

            settitle = (String) downloadsearch.titlelist2.get(yuve_start.index);
            movie_title.setText(settitle);

            movie_poster.setImageBitmap(downloadsearch.imageItems2.get(yuve_start.index).getImage());

            String setsinopsis = (String) downloadsearch.overviewlist2.get(yuve_start.index);
            setid = (String) downloadsearch.idlist2.get(yuve_start.index);

            sinopsis.setText(setsinopsis);

            String srating = (String) downloadsearch.ratings.get(yuve_start.index);
            String sgenre = (String) downloadsearch.genre.get(yuve_start.index);
            String srelease = (String) downloadsearch.releasedates.get(yuve_start.index);
            String spopularity = (String) downloadsearch.popularity.get(yuve_start.index);

            rating.setText("Rating: "+srating);
            sgenre = new ParseData().getGenre(sgenre);
            genre.setText("Genre: " + sgenre);
            release.setText("Release Date: "+ srelease);
            popularity.setText("Popularity: " + spopularity);


        }else if(yuve_start.preceding.equals("popular")) {
            DownloadPopular downloadpopular = new DownloadPopular();

            settitle = (String) downloadpopular.titlelist3.get(yuve_start.index);
            movie_title.setText(settitle);

            movie_poster.setImageBitmap(downloadpopular.imageItems3.get(yuve_start.index).getImage());

            String setsinopsis = (String) downloadpopular.overviewlist3.get(yuve_start.index);
            setid = (String) downloadpopular.idlist3.get(yuve_start.index);

            sinopsis.setText(setsinopsis);
            String srating = (String) downloadpopular.ratings.get(yuve_start.index);
            String sgenre = (String) downloadpopular.genre.get(yuve_start.index);
            String srelease = (String) downloadpopular.releasedates.get(yuve_start.index);
            String spopularity = (String) downloadpopular.popularity.get(yuve_start.index);

            rating.setText("Rating: "+srating);
            sgenre = new ParseData().getGenre(sgenre);
            genre.setText("Genre: " + sgenre);
            release.setText("Release Date: "+ srelease);
            popularity.setText("Popularity: " + spopularity);

        }else if(yuve_start.preceding.equals("watchlist")){
            DownloadWatchlist downloadwatchlist = new DownloadWatchlist();

            settitle = (String) downloadwatchlist.titlelist4.get(yuve_start.index);
            movie_title.setText(settitle);

            movie_poster.setImageBitmap(downloadwatchlist.imageItems4.get(yuve_start.index).getImage());

            String setsinopsis = (String) downloadwatchlist.overviewlist4.get(yuve_start.index);
            setid = (String) downloadwatchlist.idlist4.get(yuve_start.index);

            sinopsis.setText(setsinopsis);

            String srating = (String) downloadwatchlist.ratings.get(yuve_start.index);
            String sgenre = (String) downloadwatchlist.genre.get(yuve_start.index);
            String srelease = (String) downloadwatchlist.releasedates.get(yuve_start.index);
            String spopularity = (String) downloadwatchlist.popularity.get(yuve_start.index);

            rating.setText("Rating: "+srating);
            genre.setText("Genre: " + sgenre);
            release.setText("Release Date: "+ srelease);
            popularity.setText("Popularity: " + spopularity);


        }else if(yuve_start.preceding.equals("favorites")){
            DownloadFavorites downloadfavorites = new DownloadFavorites();

            settitle = (String) downloadfavorites.titlelist4.get(yuve_start.index);
            movie_title.setText(settitle);

            movie_poster.setImageBitmap(downloadfavorites.imageItems4.get(yuve_start.index).getImage());

            String setsinopsis = (String) downloadfavorites.overviewlist4.get(yuve_start.index);
            setid = (String) downloadfavorites.idlist4.get(yuve_start.index);

            sinopsis.setText(setsinopsis);

            String srating = (String) downloadfavorites.ratings.get(yuve_start.index);
            String sgenre = (String) downloadfavorites.genre.get(yuve_start.index);
            String srelease = (String) downloadfavorites.releasedates.get(yuve_start.index);
            String spopularity = (String) downloadfavorites.popularity.get(yuve_start.index);

            rating.setText("Rating: "+srating);
            genre.setText("Genre: " + sgenre);
            release.setText("Release Date: "+ srelease);
            popularity.setText("Popularity: " + spopularity);

        }else if(yuve_start.preceding.equals("customlist")){
            DownloadCustomList downloadcustomlist = new DownloadCustomList();

            settitle = (String) downloadcustomlist.titlelist6.get(yuve_start.index);
            movie_title.setText(settitle);

            movie_poster.setImageBitmap(downloadcustomlist.imageItems6.get(yuve_start.index).getImage());

            String setsinopsis = (String) downloadcustomlist.overviewlist6.get(yuve_start.index);
            setid = (String) downloadcustomlist.idlist6.get(yuve_start.index);

            sinopsis.setText(setsinopsis);

            String srating = (String) downloadcustomlist.ratings3.get(yuve_start.index);
            String sgenre = (String) downloadcustomlist.genre3.get(yuve_start.index);
            String srelease = (String) downloadcustomlist.releasedates3.get(yuve_start.index);
            String spopularity = (String) downloadcustomlist.popularity3.get(yuve_start.index);

            rating.setText("Rating: "+srating);
            genre.setText("Genre: " + sgenre);
            release.setText("Release Date: "+ srelease);
            popularity.setText("Popularity: " + spopularity);



        } else if(yuve_start.preceding.equals("nowplaying")) {
            DownloadNowplaying downloadnowplaying = new DownloadNowplaying();

            settitle = (String) downloadnowplaying.titlelist.get(yuve_start.index);
            movie_title.setText(settitle);

            movie_poster.setImageBitmap(downloadnowplaying.imageItems.get(yuve_start.index).getImage());


            String setsinopsis = (String) downloadnowplaying.overviewlist.get(yuve_start.index);
            setid = (String) downloadnowplaying.idlist.get(yuve_start.index);
            sinopsis.setText(setsinopsis);


            String srating = (String) downloadnowplaying.ratings.get(yuve_start.index);
            String sgenre = (String) downloadnowplaying.genre.get(yuve_start.index);
            String srelease = (String) downloadnowplaying.releasedates.get(yuve_start.index);
            String spopularity = (String) downloadnowplaying.popularity.get(yuve_start.index);

            rating.setText("Rating: " + srating);
            sgenre = new ParseData().getGenre(sgenre);
            genre.setText("Genre: " + sgenre);
            release.setText("Release Date: " + srelease);
            popularity.setText("Popularity: " + spopularity);
        }

        if(settitle.length()>=15){
            settitle = settitle.substring(0,15) + "...";
        }

        mTextView.setText(settitle);

        new DownloadTrailer().execute();//download url trailer

        //List for options menu code
        list = (ListView)findViewById(R.id.slider_menu); //Buscamos la lista por ID y la guardamos
        listadapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,  // Creamos un adaptador de arrays para poder añadir strings
                listItems);
        //Adapter to add items and track their position
        list.setAdapter(listadapter); // Asignamos el adaptador
        listadapter.add(yuve_start.ctx.getString(R.string.miyuve)); //Añadimos cosas
        listadapter.add(yuve_start.ctx.getString(R.string.Popular));
        listadapter.add(yuve_start.ctx.getString(R.string.nowplaying));
        listadapter.add(yuve_start.ctx.getString(R.string.Upcoming));
        listadapter.add(yuve_start.ctx.getString(R.string.Favorites));
        listadapter.add(yuve_start.ctx.getString(R.string.Watchlist));
        listadapter.add(yuve_start.custom_title);
        listadapter.add(yuve_start.ctx.getString(R.string.Maps));

        checkLists();
        //Listener to detect clicks and where
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Le ponemos un listener a la lista
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //Ahora hacemos que el listener actúe según la posicion clickeada.
                // La variable position toma valor 0,1 etc dependiendo del boton clickeado
                Intent intent;
                switch (position) {

                    case 0:
                        intent = new Intent(yuve_movie.this, miyuve.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(yuve_movie.this, yuve_popular.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(yuve_movie.this, yuve_nowplaying.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(yuve_movie.this, yuve_opening.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(yuve_movie.this, yuve_favorites.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(yuve_movie.this, yuve_watchlist.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(yuve_movie.this, yuve_custom.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(yuve_movie.this, yuve_maps.class);
                        startActivity(intent);
                        break;
                }

            }

        });
        //End list for options menu code

        text_search = (EditText)findViewById(R.id.search_bar);
        text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = text_search.getText().toString();
                    performSearch(query);
                    return true;
                }
                return false;
            }
        });



    }
    //Action bar icons method
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;


    }
    //End action bar icons method
    //Options menu methods
    public void openMenu(){
        ListView L = (ListView)findViewById(R.id.slider_menu);
        EditText searchText = (EditText)findViewById(R.id.search_bar);
        searchText.setVisibility(View.GONE);

        if(L.isShown()){
            L.setVisibility(View.GONE);
        }else{
            L.setVisibility(View.VISIBLE);

        }
    }
    public void openSearchBar(){
        ListView L = (ListView)findViewById(R.id.slider_menu);
        EditText searchText = (EditText)findViewById(R.id.search_bar);
        if(searchText.isShown()){
            searchText.setVisibility(View.GONE);
        }else{
            searchText.setVisibility(View.VISIBLE);
            L.setVisibility(View.GONE);

        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {// Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_menu:
                openMenu();
                return true;
            case R.id.action_search:
                openSearchBar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //End options menu methods

    public void performSearch(String q){
        //Create intent
        Bundle searchquery = new Bundle();
        searchquery.putString("search", q);
        Intent intent = new Intent(yuve_movie.this, yuve_search_results.class);
        intent.putExtras(searchquery);
        Log.d("Busqueda", q);
        //Start details activity
        startActivity(intent);
    }


    // Button functions

    public void addToWatchlist(View v){

        //Retrieve watchlist
        yuve_start.watchlist_string = yuve_start.myPref.getString("watchlist", "noids");
        SharedPreferences.Editor editor = yuve_start.myPref.edit();


        yuve_start.watchmodify = true;
        yuve_start.watchmodify2 = true;


        if(yuve_start.watchlist_string.contains("noids")){ //Check if watchlist is empty
            yuve_start.watchlist_string = setid+",";
            yuve_start.watchlist.clear();
            yuve_start.watchlist.add(setid);
            Log.d("ADDED TO WATCHLIST", setid);

        }else{                              // if not empty

            if(yuve_start.watchlist_string.contains(setid+",")) {
                if(yuve_start.watchlist.size() != 1) {
                    String s = setid + ",";
                    yuve_start.watchlist_string = yuve_start.watchlist_string.replaceAll(s, "");
                    yuve_start.watchlist.remove(setid);

                    Log.d("REMOVED FROM WATCHLIST", setid);
                }else{

                    yuve_start.watchlist_string = "noids";

                    editor.putString("watchlist", yuve_start.watchlist_string);
                    //Save watchlist

                    Log.d("CLEARED WATCHLIST", "success");
                    Log.d("WATCHLIST", yuve_start.watchlist_string);
                    editor.apply();
                }
            }else if(yuve_start.watchlist.size() != 20){
                yuve_start.watchlist_string += setid+",";
                yuve_start.watchlist.add(setid);

                Log.d("ADDED TO WATCHLIST", setid);
            }else{

                Toast.makeText(this, "Watchlist is full.", Toast.LENGTH_LONG).show();
            }
        }
        //Save watchlist
        editor.putString("watchlist", yuve_start.watchlist_string);
        editor.apply();
        checkLists();



        Log.d("WATCHLIST", yuve_start.watchlist_string);
        int i = yuve_start.watchlist.size();                           // ver el tamaño de la lista
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        String i_string = sb.toString();
        Log.d("TAMAÑO DE LA LISTA", i_string);



    }



    public void checkLists(){
        yuve_start.watchlist_string = yuve_start.myPref.getString("watchlist", "noids");
        yuve_start.favorites_string = yuve_start.myPref.getString("favorites", "noids");
        yuve_start.custom_string = yuve_start.myPref.getString("customlist", "noids");

        SharedPreferences.Editor editor = yuve_start.myPref.edit();

        Button WButt = (Button)findViewById(R.id.watchlist_button);

        if(yuve_start.watchlist_string.contains(setid)){ //Check if watchlist is empty
            WButt.setBackgroundResource(R.drawable.ic_watchlist2);

        }else if(!yuve_start.watchlist_string.contains(setid)){
            WButt.setBackgroundResource(R.drawable.ic_watchlist);

        }

        WButt = (Button)findViewById(R.id.favorite_button);
        if(yuve_start.favorites_string.contains(setid)){ //Check if watchlist is empty
            WButt.setBackgroundResource(R.drawable.ic_favorites2);

        }else if(!yuve_start.favorites_string.contains(setid)){
            WButt.setBackgroundResource(R.drawable.ic_favorites);

        }

        WButt = (Button)findViewById(R.id.customlist_button);
        if(yuve_start.custom_string.contains(setid)){ //Check if watchlist is empty
            WButt.setBackgroundResource(R.drawable.ic_more2);

        }else if(!yuve_start.custom_string.contains(setid)){
            WButt.setBackgroundResource(R.drawable.ic_more);

        }

    }
    public void playTrailer(View v){
        Log.d("TRAILER URL", trailer);
        if(trailer.equals(yuve_start.ctx.getResources().getString(R.string.YoutubeURL))||trailer.equals("notrailer")){
            Toast.makeText(yuve_movie.this, "No trailer available", Toast.LENGTH_SHORT).show();
        }else{
            Intent trailer_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer));
            startActivity(trailer_intent);
        }
    }

    public void addToFavorites(View v){

        //Retrieve watchlist
        yuve_start.favorites_string = yuve_start.myPref.getString("favorites", "noids");
        SharedPreferences.Editor editor = yuve_start.myPref.edit();


        yuve_start.favmodify = true;
        yuve_start.favmodify2 = true;


        if(yuve_start.favorites_string.contains("noids")){ //Check if watchlist is empty
            yuve_start.favorites_string = setid+",";
            yuve_start.favorites.clear();
            yuve_start.favorites.add(setid);
            Log.d("ADDED TO FAVORITES", setid);

        }else{                              // if not empty

            if(yuve_start.favorites_string.contains(setid+",")) {
                if(yuve_start.favorites.size() != 1) {
                    String s = setid + ",";
                    yuve_start.favorites_string = yuve_start.favorites_string.replaceAll(s, "");
                    yuve_start.favorites.remove(setid);

                    Log.d("REMOVED FROM FAVORITES", setid);
                }else{

                    yuve_start.favorites_string = "noids";

                    editor.putString("favorites", yuve_start.favorites_string);
                    //Save watchlist

                    Log.d("CLEARED FAVORITES", "success");
                    Log.d("FAVORITES", yuve_start.favorites_string);
                    editor.apply();
                }
            }else if(yuve_start.favorites.size() != 20){
                yuve_start.favorites_string += setid+",";
                yuve_start.favorites.add(setid);

                Log.d("ADDED TO FAVORITES", setid);
            }else{

                Toast.makeText(this, "Favorites is full.", Toast.LENGTH_LONG).show();
            }
        }
        //Save watchlist
        editor.putString("favorites", yuve_start.favorites_string);
        editor.apply();
        checkLists();



        Log.d("FAVORITES", yuve_start.favorites_string);
        int i = yuve_start.favorites.size();                           // ver el tamaño de la lista
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        String i_string = sb.toString();
        Log.d("TAMAÑO DE LA LISTA", i_string);



    }

    public void addToCustom(View v){


        yuve_start.custom_string = yuve_start.myPref.getString("customlist", "noids");
        SharedPreferences.Editor editor = yuve_start.myPref.edit();


        yuve_start.custommodify = true;
        yuve_start.custommodify2 = true;


        if(yuve_start.custom_string.contains("noids")){ //Check if watchlist is empty
            yuve_start.custom_string = setid+",";
            yuve_start.customlist.clear();
            yuve_start.customlist.add(setid);
            Log.d("ADDED TO CUSTOMLIST", setid);

        }else{                              // if not empty

            if(yuve_start.custom_string.contains(setid+",")) {
                if(yuve_start.customlist.size() != 1) {
                    String s = setid + ",";
                    yuve_start.custom_string = yuve_start.custom_string.replaceAll(s, "");
                    yuve_start.customlist.remove(setid);

                    Log.d("REMOVED FROM CUSTOMLIST", setid);
                }else{

                    yuve_start.custom_string = "noids";

                    editor.putString("customlist", yuve_start.custom_string);
                    //Save watchlist

                    Log.d("CLEARED CUSTOMLIST", "success");
                    Log.d("CUSTOMLIST", yuve_start.custom_string);
                    editor.apply();
                }
            }else if(yuve_start.customlist.size() != 20){
                yuve_start.custom_string += setid+",";
                yuve_start.customlist.add(setid);

                Log.d("ADDED TO CUSTOMLIST", setid);
            }else{

                Toast.makeText(this, "The list is full.", Toast.LENGTH_LONG).show();
            }
        }
        //Save watchlist
        editor.putString("customlist", yuve_start.custom_string);
        editor.apply();
        checkLists();



        Log.d("CUSTOMLIST", yuve_start.custom_string);
        int i = yuve_start.customlist.size();                           // ver el tamaño de la lista
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        String i_string = sb.toString();
        Log.d("TAMAÑO DE LA LISTA", i_string);



    }
}


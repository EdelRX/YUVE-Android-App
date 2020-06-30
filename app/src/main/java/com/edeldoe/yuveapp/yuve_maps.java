package com.edeldoe.yuveapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * Created by EdelRX on 23/04/2016.
 */
public class yuve_maps extends FragmentActivity  {


    public Menu main_menu;
    public EditText text_search;

    public static Activity activity; // Create activity instance to use in other classes
    public static Marker[] placeMarkers;
    private final int MAX_PLACES = 20;
    public static MarkerOptions[] places;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> listadapter;
    private ListView list;
    public static Boolean is_connected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this; // Set reference to this activity to reference from other classes


        placeMarkers = new Marker[MAX_PLACES];

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            is_connected = true; // Call the asynctask to download upcoming movie info
        } else {
            is_connected = false;
            Toast.makeText(activity, "No internet connection.", Toast.LENGTH_LONG).show();
        }

        // Action bar code
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar);
        TextView mTextView = (TextView) findViewById(R.id.title);
        mTextView.setText(yuve_start.ctx.getString(R.string.Maps));
        setContentView(R.layout.activity_yuve_maps);
        getActionBar().setIcon(new ColorDrawable((getResources().getColor(android.R.color.transparent))));
        //End action bar code

        //List for options menu code
        list = (ListView) findViewById(R.id.slider_menu); //Buscamos la lista por ID y la guardamos aqui
        listadapter = new ArrayAdapter<>(this,
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

        //Listener to detect clicks and where
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Le ponemos un listener a la lista
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //Ahora hacemos que el listener actúe según la posicion clickeada.
                // La variable position toma valor 0,1 etc dependiendo del boton clickeado
                Intent intent;
                switch (position) {

                    case 0:
                        intent = new Intent(yuve_maps.this, miyuve.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(yuve_maps.this, yuve_popular.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(yuve_maps.this, yuve_nowplaying.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(yuve_maps.this, yuve_opening.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(yuve_maps.this, yuve_favorites.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(yuve_maps.this, yuve_watchlist.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(yuve_maps.this, yuve_custom.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(yuve_maps.this, yuve_maps.class);
                        startActivity(intent);
                        break;
                }
            }

        });
        text_search = (EditText) findViewById(R.id.search_bar);
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


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    //End list for options menu code

    //Action bar icons menu

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        main_menu = menu;
        return true;
    }
    //End action bar icons menu


    //Options menu methods
    public void openMenu() {
        ListView L = (ListView) findViewById(R.id.slider_menu);
        EditText searchText = (EditText) findViewById(R.id.search_bar);
        searchText.setVisibility(View.GONE);

        if (L.isShown()) {
            L.setVisibility(View.GONE);
        } else {
            L.setVisibility(View.VISIBLE);

        }
    }

    public void openSearchBar() {
        ListView L = (ListView) findViewById(R.id.slider_menu);
        EditText searchText = (EditText) findViewById(R.id.search_bar);
        if (searchText.isShown()) {
            searchText.setVisibility(View.GONE);
        } else {
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
    // Search method
    public void performSearch(String q) {
        //Create intent
        Bundle searchquery = new Bundle();
        searchquery.putString("search", q);
        Intent intent = new Intent(yuve_maps.this, yuve_search_results.class);
        intent.putExtras(searchquery);
        Log.d("Busqueda", q);
        //Start details activity
        startActivity(intent);
    }






}


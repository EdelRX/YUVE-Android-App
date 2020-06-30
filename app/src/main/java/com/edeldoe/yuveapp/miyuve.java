package com.edeldoe.yuveapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class miyuve extends Activity {
    public static GridView gridView;
    public static GridView gridView2;
    public static GridView gridView3;
    private ListView list;
    public Menu main_menu;
    public EditText text_search;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> listadapter;
    public static Activity activity; // Create activity instance to use in other classes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this; // Set reference to this activity to reference from other classes
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadMiyuve().execute(); // Call the asynctask to download watchlist movie info
        } else {
            Toast.makeText(activity, "No internet connection.", Toast.LENGTH_LONG).show();
        }

        // Action bar code
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar);
        TextView mTextView = (TextView) findViewById(R.id.title);
        mTextView.setText(yuve_start.ctx.getString(R.string.miyuve));
        setContentView(R.layout.activity_miyuve);
        getActionBar().setIcon(new ColorDrawable((getResources().getColor(android.R.color.transparent))));
        //End action bar code

        //Movie grid code
        gridView = (GridView) findViewById(R.id.favorites_grid); // Buscamos la lista por ID y la guardamos aqui
        //gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, DownloadOpening.imageItems); //Creamos un adaptador y le metemos las imagenes obtenidas en getData()
        //gridView.setAdapter(gridAdapter); //Le asignamos el adaptador a la lista para que se quede las imagenes que ahora tiene el adaptador
        gridView2 = (GridView) findViewById(R.id.watchlist_grid); // Buscamos la lista por ID y la guardamos aqui
        gridView3 = (GridView) findViewById(R.id.lista_personalizada); // Buscamos la lista por ID y la guardamos aqui

        TextView list1 = (TextView) findViewById(R.id.lista);
        list1.setText(yuve_start.custom_title);


        //Asignamos por ahora, un simple listener al grid
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent;
                intent = new Intent(miyuve.this, yuve_favorites.class);
                startActivity(intent);
            }
        });
        gridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }

        });

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent;
                intent = new Intent(miyuve.this, yuve_watchlist.class);
                startActivity(intent);
            }
        });
        gridView2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }

        });
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent;
                intent = new Intent(miyuve.this, yuve_custom.class);
                startActivity(intent);
            }
        });
        gridView3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }

        });


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
                        intent = new Intent(miyuve.this, miyuve.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(miyuve.this, yuve_popular.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(miyuve.this, yuve_nowplaying.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(miyuve.this, yuve_opening.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(miyuve.this, yuve_favorites.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(miyuve.this, yuve_watchlist.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(miyuve.this, yuve_custom.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(miyuve.this, yuve_maps.class);
                        startActivity(intent);
                        break;
                }

            }

        });
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
    @Override
    protected void onResume() {
        super.onResume();
        activity = this; // Set reference to this activity to reference from other classes
        if(yuve_start.favmodify2) {
            miyuve.activity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                DownloadMiyuve.imageItems4 = new ArrayList<>();
                new DownloadMiyuve().execute(); // Call the asynctask to download watchlist movie info
            } else {
                Toast.makeText(activity, "No internet connection.", Toast.LENGTH_LONG).show();
            }
            yuve_start.favmodify2 = false;
        }

        if(yuve_start.watchmodify2) {
            miyuve.activity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            gridView2.setVisibility(View.INVISIBLE);
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                DownloadMiyuve.imageItems5 = new ArrayList<>();
                new DownloadMiyuve().execute(); // Call the asynctask to download watchlist movie info
            } else {
                Toast.makeText(activity, "No internet connection.", Toast.LENGTH_LONG).show();
            }
            yuve_start.watchmodify2 = false;
        }
        if(yuve_start.custommodify2) {
            miyuve.activity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            gridView3.setVisibility(View.INVISIBLE);
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                DownloadMiyuve.imageItems6 = new ArrayList<>();
                new DownloadMiyuve().execute(); // Call the asynctask to download watchlist movie info
            } else {
                Toast.makeText(activity, "No internet connection.", Toast.LENGTH_LONG).show();
            }
            yuve_start.custommodify2 = false;
        }


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
    public void performSearch(String q){
        //Create intent
        Bundle searchquery = new Bundle();
        searchquery.putString("search", q);
        Intent intent = new Intent(miyuve.this, yuve_search_results.class);
        intent.putExtras(searchquery);
        Log.d("Busqueda", q);
        //Start details activity
        startActivity(intent);
    }

    public void handlelist(View v){

        final EditText edittitle = (EditText)findViewById(R.id.listname);
        edittitle.setVisibility(View.VISIBLE);



        edittitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String title = edittitle.getText().toString();
                    TextView list1 = (TextView) findViewById(R.id.lista);
                    list1.setText(title);
                    yuve_start.custom_title = list1.getText().toString();
                    SharedPreferences.Editor editor = yuve_start.myPref.edit();
                    editor.putString("customtitle", yuve_start.custom_title);
                    editor.apply();
                    refreshmenu();
                    edittitle.setVisibility(View.GONE);
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });


    }

    public void removehint(View v){
        final EditText edittitle = (EditText)findViewById(R.id.listname);
        edittitle.setHint("");

    }

    public void refreshmenu(){

        //List for options menu code
        list = (ListView) findViewById(R.id.slider_menu); //Buscamos la lista por ID y la guardamos aqui
        ArrayList<String> listItems=new ArrayList<String>();
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
                        intent = new Intent(miyuve.this, miyuve.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(miyuve.this, yuve_popular.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(miyuve.this, yuve_nowplaying.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(miyuve.this, yuve_opening.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(miyuve.this, yuve_favorites.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(miyuve.this, yuve_watchlist.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(miyuve.this, yuve_custom.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(miyuve.this, yuve_maps.class);
                        startActivity(intent);
                        break;
                }

            }

        });

    }





}

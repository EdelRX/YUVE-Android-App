package com.edeldoe.yuveapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParseData {

    public List readJsonStream(String response,int choice) throws IOException {

        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray results = object.getJSONArray("results");
            List list = new ArrayList();

            String poster_path = null;
            String title = null;
            String overview = null;
            String id = null;

           if(choice == 1) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   poster_path = json_data.getString("poster_path");

                   list.add(poster_path);


               }

           }else if(choice == 2) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   title = json_data.getString("original_title");

                   list.add(title);
               }

           }else if(choice == 3) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   overview = json_data.getString("overview");

                   list.add(overview);
               }

           }else if(choice == 4) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   id = json_data.getString("id");

                   list.add(id);
               }
           }else if(choice == 5) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   id = json_data.getString("vote_average");

                   list.add(id);
               }
           }else if(choice == 6) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   id = json_data.getString("genre_ids");

                   list.add(id);
               }
           }else if(choice == 7) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   id = json_data.getString("release_date");

                   list.add(id);
               }
           }else if(choice == 8) {


               for (int i = 0; i < results.length(); i++) {

                   JSONObject json_data = results.getJSONObject(i);
                   id = json_data.getString("popularity");

                   list.add(id);
               }
           }



            return list;

        } catch (JSONException e) {
            return null;
        }

    }
    public String readResult(String response,int choice) throws IOException {


        try {
            String s = null;
            JSONObject results = (JSONObject) new JSONTokener(response).nextValue();


            String poster_path = null;
            String title = null;
            String overview = null;
            String id = null;
            String pages = null;


            if(choice == 1) {

                //JSONObject json_data = results.getJSONObject(0);
                poster_path = results.getString("poster_path");


                s = poster_path;
                Log.d("POSTERPATH",s);

            }else if(choice == 2) {

                //JSONObject json_data = results.getJSONObject(0);
                title = results.getString("original_title");


                s=title;
                Log.d("TITLE",s);

            }else if(choice == 3) {

                //JSONObject json_data = results.getJSONObject(0);
                overview = results.getString("overview");
                Log.d("OVERVIEW",overview);

                s = overview;


            }else if(choice == 4) {
                //JSONObject json_data = results.getJSONObject(0);
                id = results.getString("id");
                s = id;
                Log.d("ID",s);

            }else if(choice == 5){

                s = results.getString("total_pages");

            }
            else if(choice == 6){

                s = results.getString("vote_average");

            }
            else if(choice == 7){

                s = results.getString("genres");

            }
            else if(choice == 8){

                s = results.getString("release_date");

            }
            else if(choice == 9){

                s = results.getString("popularity");

            }



            return s;

        } catch (JSONException e){
            Log.d("EXCEPTION", "Cannot read JSON Object");
            return null;
        }

    }





    public String readJsonTrailer(String response) throws IOException {

        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray results = object.getJSONArray("results");

            String trailerurl = yuve_start.ctx.getResources().getString(R.string.YoutubeURL);
            if(results.length()>0) {
                JSONObject json_data = results.getJSONObject(0);
                String s = json_data.getString("key");

                trailerurl += s;

            }



            return trailerurl;

        } catch (JSONException e) {
            return "notrailer";
        }

    }

    public String getGenre(String genres){

        genres = genres.replaceAll(",", " ");
        genres = genres.replaceAll("\\[", "");
        genres = genres.replaceAll("\\]", "");
        Log.d("GENEROS: ", genres);
        Scanner sc = new Scanner(genres);
        int ID;
        String g = "";
        List<Integer> l = new ArrayList<Integer>();
        while(sc.hasNextInt()){
            l.add(sc.nextInt());
        }
        for(int i = 0; i<l.size(); i++){
            ID = l.get(i);
            switch(ID){
                case 28:
                    g += "Action, ";
                    break;
                case 12:
                    g += "Adventure, ";

                    break;
                case 16:
                    g +=  "Animation, ";
                    break;
                case 35:
                    g +=  "Comedy, ";
                    break;
                case 80:
                    g +=  "Crime, ";
                    break;
                case 99:
                    g +=  "Documentary, ";
                    break;
                case 18:
                    g +=  "Drama, ";
                    break;
                case 10751:
                    g +=  "Family, ";
                    break;
                case 14:
                    g +=  "Fantasy, ";
                    break;
                case 10769:
                    g +=  "Foreign, ";
                    break;
                case 36:
                    g +=  "History, ";
                    break;
                case 27:
                    g +=  "Horror, ";
                    break;
                case 10402:
                    g +=  "Music, ";
                    break;
                case 9648:
                    g +=  "Mystery, ";
                    break;
                case 10749:
                    g +=  "Romance, ";
                    break;
                case 878:
                    g +=  "Science Fiction, ";
                    break;
                case 10770:
                    g +=  "TV Movie, ";
                    break;
                case 53:
                    g +=  "Thriller, ";
                    break;
                case 10752:
                    g +=  "War, ";
                    break;
                case 37:
                    g +=  "Western, ";
                    break;
                default:
                    g += "No genre, ";
                    break;
            }
        }
        if(g.equals("")){
            g = "Undefined";
        }else{
            g = g.substring(0, g.length()-2);
        }
        return g;
    }

    public String getGenreId(String response){
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray genres = object.getJSONArray("genres");

            String s = "";
            for (int i = 0; i < genres.length(); i++) {

                JSONObject json_data = genres.getJSONObject(i);
                s += json_data.getString("name")+", ";
            }
            if(s.equals("")){
                s = "Undefined";
            }else{
                s = s.substring(0, s.length()-2);
            }
            return s;
        }catch (JSONException e) {
            return "Undefined";
        }
    }






}

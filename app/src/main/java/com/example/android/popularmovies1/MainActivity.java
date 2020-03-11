package com.example.android.popularmovies1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String MOVIESDB_URL = MovieAPI.API_URL;
    private Movies movies;
    private GridView gridView;
    public SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gridview);
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        String menuSelection = pref.getString("menu_selection","popularity");
        String sortBy = menuSelection.equals("popularity") ? "popularity.desc" : "vote_average.desc";

        fetchMovieData(sortBy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences sharedPreferences;

        switch (id){
            case R.id.item1:
                storeInSharedPreferences("popularity");
                fetchMovieData("popularity.desc");
                return true;
            case R.id.item2:
                storeInSharedPreferences("rating");
                fetchMovieData("vote_average.desc");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A method to help saving values into SharedPreferences
     * @param sortBy      The sorting criteria chosen by the user
     */
    private void storeInSharedPreferences(String sortBy) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("menu_selection", sortBy);
        editor.commit();
    }

    /**
     * A method to fetch movies from the MoviesDB API
     * @param sortBy      The sorting criteria chosen by the user
     */
    private void fetchMovieData(String sortBy) {
        RequestParams params = new RequestParams();
        params.put("api_key", getResources().getString(R.string.api_key));
        params.put("sort_by", sortBy);

        // AsyncHttpClient belongs to the loopj dependency.
        AsyncHttpClient client = new AsyncHttpClient();

        // Making an HTTP GET request by providing a URL and the parameters.
        // The JsonHttpResponseHandler is the object that will be notified
        // of the response to this GET request
        client.get(MOVIESDB_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                MainActivity.this.displayData(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                MainActivity.this.startNoInternetActivity();
            }
        });
    }

    /**
     * A method to help saving values into SharedPreferences
     * @param response      The JSONObject response from the API call
     */
    public void displayData(JSONObject response) {
        Gson gson = new Gson();
        movies = gson.fromJson(response.toString(), Movies.class);
        final Movie[] popularMovies = movies.results;
        MoviesAdapter moviesAdapter = new MoviesAdapter(this, popularMovies);
        gridView.setAdapter(moviesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            final public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = popularMovies[position];
                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);

                intent.putExtra("id", movie.getId());
                intent.putExtra("poster_path", movie.getPoster_path());
                intent.putExtra("overview", movie.getOverview());
                intent.putExtra("original_title", movie.getOriginal_title());
                intent.putExtra("release_date", movie.getRelease_date());
                intent.putExtra("vote_average", movie.getVote_average());
                intent.putExtra("position", position);

                startActivity(intent);
            }
        });
    }

    /**
     * A method to handle the lack of connectivity
     */
    public void startNoInternetActivity() {
        Intent intent = new Intent(getApplicationContext(), NoInternetActivity.class);
        startActivity(intent);
    };
}

package com.example.android.popularmovies1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * A class to handle the Movie Details activity
 */
public class MovieDetailsActivity extends AppCompatActivity {

    public static Movie movie;
    public static Intent intent;
    public static TextView movie_title, movie_release_date, movie_average_rating, movie_overview;
    public static ImageView movie_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intentMainActivity = getIntent();
        int position = intentMainActivity.getIntExtra("id", 0);
        String posterPath = intentMainActivity.getStringExtra("poster_path");
        String overview = intentMainActivity.getStringExtra("overview");
        String originalTitle = intentMainActivity.getStringExtra("original_title");
        String releaseDate = intentMainActivity.getStringExtra("release_date");
        String voteAverage = intentMainActivity.getStringExtra("vote_average");

        movie_title = (TextView) findViewById(R.id.movie_title);
        movie_release_date = (TextView) findViewById(R.id.movie_release_date);
        movie_average_rating = (TextView) findViewById(R.id.movie_average_rating);
        movie_poster = (ImageView) findViewById(R.id.movie_poster);
        movie_overview = (TextView) findViewById(R.id.movie_overview);

        movie_title.setText(originalTitle);

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("MMMM dd, yyyy").format(date);
        movie_release_date.setText(formattedDate);
        movie_average_rating.setText("Rating: " + voteAverage  + "/10");
        movie_overview.setText(overview);


        if (posterPath == null) {
            movie_poster.setImageResource(R.drawable.noimage);
        } else {
            // String movie_poster_url = "https://image.tmdb.org/t/p/w500/" + posterPath;
            String movie_poster_url = MovieAPI.IMAGE_URL + MovieAPI.IMAGE_SIZE_185 + posterPath;

            Picasso.with(this).load(movie_poster_url).into(movie_poster);
        }
    }
}

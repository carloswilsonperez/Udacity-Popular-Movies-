package com.example.android.popularmovies1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A class to extend the BaseAdapter class and manage the rendering of the list of movies.
 */
public class MoviesAdapter extends BaseAdapter {

    private final Context mContext;
    private final Movie[] movies;

    // 1
    public MoviesAdapter(Context context, Movie[] movies) {
        this.mContext = context;
        this.movies = movies;
    }

    // 2
    @Override
    public int getCount() {
        return movies.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Movie movie = movies[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_movie_layout, null);
        }

        // 3
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.image_poster);
        final TextView movieTitle = (TextView) convertView.findViewById(R.id.image_title);
        movieTitle.setVisibility(View.GONE);

        Picasso
                .with(mContext)
                .load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_path())
                .placeholder(R.drawable.loading)
                .into(imageView);

        if (movie.getPoster_path() == null) {
            imageView.setImageResource(R.drawable.noimage);
            movieTitle.setVisibility(View.VISIBLE);
            movieTitle.setText(movie.getOriginal_title());
        }

        return convertView;
    }
}
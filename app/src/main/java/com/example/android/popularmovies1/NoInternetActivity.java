package com.example.android.popularmovies1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * A class to manae the Activity to be shown when Internet fails
 */
public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }
}

package com.example.android.popularmovies1;

import java.util.Arrays;

/**
 * A class to help GSON to de-serialize the API response
 */
public class Movies
{
    private String id;
    private String page;
    private String page_results;
    private String total_pages;

    public Movie[] results;
}

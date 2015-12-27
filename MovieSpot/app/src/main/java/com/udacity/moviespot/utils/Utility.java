package com.udacity.moviespot.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.udacity.moviespot.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by GodwinRoseSamuel on 24-Dec-15.
 */
public class Utility {
    public static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3";
    public static final String MOVIES_IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String API_KEY = "";
    private static final String DISCOVER_MOVIES_API = "discover/movie";
    private static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String OUTPUT_DATE_FORMAT = "MMM yyyy";
    public static final String MOVIE_KEY = "com.udacity.moviespot.movie.par";
    public static final String MOVIES_KEY = "com.udacity.moviespot.movies.par";
    public static final String UNKNOWN = "Unknown";

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        }
        return false;
    }

    public static String getDiscoverMoviesURL() {
        return MOVIES_BASE_URL + "/" + DISCOVER_MOVIES_API;
    }

    public static String getImageUrl(String width, String path) {
        return MOVIES_IMAGE_BASE_URL + "/" + width + path;
    }

    public static String getRoundedPercentage(Double percentage) {
        return String.valueOf(Math.round(percentage));
    }

    public static String formatDate(String date) throws ParseException {
        return date.isEmpty() ? UNKNOWN : new SimpleDateFormat(OUTPUT_DATE_FORMAT).format(new SimpleDateFormat(INPUT_DATE_FORMAT).parse(date));
    }

    public static String getPreferredSortingOrder(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sort_key), context.getString(R.string.pref_sortby_default));
    }
}

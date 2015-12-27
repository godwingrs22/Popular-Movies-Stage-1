package com.udacity.moviespot.core;

import android.net.Uri;
import android.os.Parcel;
import android.util.Log;

import com.udacity.moviespot.model.Movie;
import com.udacity.moviespot.utils.ServiceManager;
import com.udacity.moviespot.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by GodwinRoseSamuel on 24-Dec-15.
 */
public class MovieManager {
    private static final String TAG = MovieManager.class.getSimpleName();
    private static final String APIKEY_PARAM = "api_key";
    private static final String PAGE_PARAM = "page";
    private static final String SORTBY_PARAM = "sort_by";
    private ServiceManager serviceManager = null;

    public List<Movie> loadMovies(final String page, final String sortBy) {
        serviceManager = new ServiceManager();
        String movieDataResponse = null;
        final Uri builtUri = Uri.parse(Utility.getDiscoverMoviesURL()).buildUpon()
                .appendQueryParameter(PAGE_PARAM, page)
                .appendQueryParameter(SORTBY_PARAM, sortBy)
                .appendQueryParameter(APIKEY_PARAM, Utility.API_KEY)
                .build();
        try {
            URL url = new URL(builtUri.toString());
            movieDataResponse = serviceManager.recieveResponse(url, "GET");

        } catch (MalformedURLException e) {
            Log.e(TAG, "Error ", e);
        }

//        Log.v(TAG,"<---Json Response--->"+ movieDataResponse);
        return getMoviesDataFromJson(movieDataResponse);
    }

    private List<Movie> getMoviesDataFromJson(final String movieData) {
        List<Movie> movies = new LinkedList<>();
        final String MOVIES_LIST = "results";
        final String MOVIE_TITLE = "title";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_RATING = "vote_average";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_VOTES = "vote_count";

        try {
            JSONObject moviesJson = new JSONObject(movieData);
            JSONArray moviesList = moviesJson.getJSONArray(MOVIES_LIST);
            for (int i = 0; i < moviesList.length(); i++) {
                JSONObject movieJSON = (JSONObject) moviesList.get(i);
                String title = movieJSON.getString(MOVIE_TITLE);
                String posterPath = movieJSON.getString(MOVIE_POSTER_PATH);
                String backdropPath = movieJSON.getString(MOVIE_BACKDROP_PATH);
                String overview = movieJSON.getString(MOVIE_OVERVIEW);
                String releaseDate = movieJSON.getString(MOVIE_RELEASE_DATE);
                String userRating = movieJSON.getString(MOVIE_RATING);
                Double popularity = movieJSON.getDouble(MOVIE_POPULARITY);
                String votes = movieJSON.getString(MOVIE_VOTES);

                Movie movie = new Movie();
                movie.setTitle(title);
                movie.setPosterImage(posterPath);
                movie.setBackdropImage(backdropPath != "null" ? backdropPath : posterPath);
                movie.setOverview(overview.isEmpty() ? Utility.UNKNOWN : overview);
                movie.setReleaseDate(Utility.formatDate(releaseDate));
                movie.setUserRating(userRating);
                movie.setPopularity(Utility.getRoundedPercentage(popularity));
                movie.setVotes(votes);

                movies.add(movie);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Parsing err" + e);
        } catch (ParseException e) {
            Log.e(TAG, "Date Parsing err" + e);
        }

//        Log.v(TAG,"<---Movies--->"+ Arrays.asList(movies).toString());
        return movies;
    }
}

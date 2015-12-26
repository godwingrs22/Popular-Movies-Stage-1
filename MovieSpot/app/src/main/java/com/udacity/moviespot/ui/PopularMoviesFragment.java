package com.udacity.moviespot.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.udacity.moviespot.R;
import com.udacity.moviespot.core.MovieManager;
import com.udacity.moviespot.model.Movie;
import com.udacity.moviespot.utils.Utility;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by GodwinRoseSamuel on 24-Dec-15.
 */
public class PopularMoviesFragment extends Fragment {
    private static final String TAG = PopularMoviesFragment.class.getSimpleName();

    @Bind(R.id.gridview_popular_movies)
    GridView moviesGridView;

    private List<Movie> movies;
    private MoviesAdapter moviesAdapter;
    private MovieManager movieManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        ButterKnife.bind(this, view);
        movies = new LinkedList<>();
        movieManager = new MovieManager();

        moviesAdapter = new MoviesAdapter(getContext(), movies);
        moviesGridView.setAdapter(moviesAdapter);

        return view;
    }

    @OnItemClick(R.id.gridview_popular_movies)
    public void callDetailActivity(int position) {
        Movie movie = movies.get(position);
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class).putExtra(Utility.MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        if (Utility.isInternetConnected(getContext())) {
            FetchMoviesTask weatherTask = new FetchMoviesTask();
            String page = "1";
            String sortBy = Utility.getPreferredSortingOrder(getContext());
            weatherTask.execute(page, sortBy);
        } else {
            notConnected();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.fragment_popular_movies_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dialog);
            progressDialog.setTitle("Please Wait..");
            progressDialog.setMessage("Fetching Movies List...!!");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return movieManager.loadMovies(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            movies.clear();
            movies.addAll(movieList);
            moviesAdapter.notifyDataSetChanged();
        }
    }

    private void notConnected() {
        new AlertDialog.Builder(getContext(), R.style.AppTheme_Dialog)
                .setTitle(getString(R.string.dialog_no_network))
                .setMessage(getString(R.string.dialog_no_network_info))
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateMovies();
                    }
                })
                .show();
    }
}

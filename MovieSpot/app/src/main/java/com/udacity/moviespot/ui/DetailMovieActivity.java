package com.udacity.moviespot.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.moviespot.R;
import com.udacity.moviespot.model.Movie;
import com.udacity.moviespot.utils.Utility;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GodwinRoseSamuel on 25-Dec-15.
 */
public class DetailMovieActivity extends AppCompatActivity {

    private static final String TAG = DetailMovieActivity.class.getSimpleName();
    private Movie movie;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.fab_share)
    FloatingActionButton fabShare;
    @Bind(R.id.detail_movie_release_date_textview)
    TextView releaseDateTextView;
    @Bind(R.id.detail_movie_overview_textview)
    TextView overviewTextView;
    @Bind(R.id.detail_movie_rating_textview)
    TextView ratingTextView;
    @Bind(R.id.detail_movie_popularity_textview)
    TextView popularityTextView;
    @Bind(R.id.detail_movie_votes_textview)
    TextView votesTextView;
    @Bind(R.id.detail_movie_backdrop)
    ImageView movieBackdropImage;
    @Bind(R.id.detail_movie_poster)
    ImageView moviePosterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        movie = (Movie) getIntent().getParcelableExtra(Utility.MOVIE_KEY);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle(movie.getTitle());

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                share.putExtra(Intent.EXTRA_SUBJECT, movie.getTitle());
                share.putExtra(Intent.EXTRA_TEXT, movie.getOverview());
                startActivity(Intent.createChooser(share, getString(R.string.app_name)));
            }
        });

        setMovieImages();

        releaseDateTextView.setText(movie.getReleaseDate());
        overviewTextView.setText(movie.getOverview());
        ratingTextView.setText(movie.getUserRating());
        popularityTextView.setText(movie.getPopularity());
        votesTextView.setText(movie.getVotes());
    }

    private void setMovieImages() {
        final String POSTER_WIDTH = "w342";
        final String BACKDROP_WIDTH = "w342";

        String posterImageUrl = Utility.getImageUrl(POSTER_WIDTH, movie.getPosterImage());
        String backdropImageUrl = Utility.getImageUrl(BACKDROP_WIDTH, movie.getBackdropImage());

        Picasso.with(this)
                .load(posterImageUrl)
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.error_placeholder)
                .into(moviePosterImage);

        Picasso.with(this)
                .load(backdropImageUrl)
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.error_placeholder)
                .into(movieBackdropImage);

        moviePosterImage.setAdjustViewBounds(true);
    }
}

package com.udacity.moviespot.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.moviespot.R;
import com.udacity.moviespot.model.Movie;
import com.udacity.moviespot.utils.Utility;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GodwinRoseSamuel on 24-Dec-15.
 */
public class MoviesAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final MoviesViewHolder moviesViewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_movie_popular, parent, false);
            moviesViewHolder = new MoviesViewHolder(convertView);
            convertView.setTag(moviesViewHolder);
        } else {
            moviesViewHolder = (MoviesViewHolder) convertView.getTag();
        }

        moviesViewHolder.titleTextView.setText(movies.get(position).getTitle());
        moviesViewHolder.releaseDateTextView.setText(movies.get(position).getReleaseDate());
        moviesViewHolder.ratingTextView.setText(movies.get(position).getUserRating());
        moviesViewHolder.popularityTextView.setText(movies.get(position).getPopularity());

        String posterImageUrl = Utility.getImageUrl("w500", movies.get(position).getPosterImage());
        Picasso.with(context)
                .load(posterImageUrl)
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.error_placeholder)
                .into(moviesViewHolder.posterImageView);
        moviesViewHolder.posterImageView.setAdjustViewBounds(true);

        moviesViewHolder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, moviesViewHolder.viewMore);
                popup.getMenuInflater().inflate(R.menu.movie_pop_up_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.add_favourite)
                            Toast.makeText(context, movies.get(position).getTitle() + " is add to your favourite list!!", Toast.LENGTH_SHORT).show();
                        else if (id == R.id.add_share) {
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("text/plain");
                            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            share.putExtra(Intent.EXTRA_SUBJECT, movies.get(position).getTitle());
                            share.putExtra(Intent.EXTRA_TEXT, movies.get(position).getOverview());
                            context.startActivity(Intent.createChooser(share, context.getString(R.string.app_name)));
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        return convertView;
    }

    public static class MoviesViewHolder {
        @Bind(R.id.list_movie_poster)
        ImageView posterImageView;
        @Bind(R.id.list_movie_title_textview)
        TextView titleTextView;
        @Bind(R.id.list_movie_release_date_textview)
        TextView releaseDateTextView;
        @Bind(R.id.list_movie_rating_textview)
        TextView ratingTextView;
        @Bind(R.id.list_movie_popularity_textview)
        TextView popularityTextView;
        @Bind(R.id.list_movie_more)
        ImageView viewMore;

        public MoviesViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

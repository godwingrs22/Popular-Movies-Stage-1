package com.udacity.moviespot.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GodwinRoseSamuel on 24-Dec-15.
 */
public class Movie implements Parcelable {
    private String title;
    private String posterImage;
    private String backdropImage;
    private String overview;
    private String releaseDate;
    private String userRating;
    private String popularity;
    private String votes;

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            Movie movie = new Movie();
            movie.title = in.readString();
            movie.posterImage = in.readString();
            movie.backdropImage = in.readString();
            movie.overview = in.readString();
            movie.releaseDate = in.readString();
            movie.userRating = in.readString();
            movie.popularity = in.readString();
            movie.votes = in.readString();
            return movie;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getBackdropImage() {
        return backdropImage;
    }

    public void setBackdropImage(String backdropImage) {
        this.backdropImage = backdropImage;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", posterImage='" + posterImage + '\'' +
                ", backdropImage='" + backdropImage + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", userRating='" + userRating + '\'' +
                ", popularity='" + popularity + '\'' +
                ", votes='" + votes + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterImage);
        dest.writeString(backdropImage);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(userRating);
        dest.writeString(popularity);
        dest.writeString(votes);
    }
}

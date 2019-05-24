package com.example.a80_tech_task.Entities;

import android.support.v7.util.DiffUtil;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by azeddine on 3/27/18.
 */

public class Movie {
    private int id;
    private int imdbId;
    private String originalTitle;
    private String title;
    private Date runTime;
    private String status;
    @SerializedName("tagline")
    private String tagLine;
    private Date releaseDate;
    private String directorFullName;
    private String writerFullName;
    private int voteCount;
    private float voteAverage;
    private List<String> genres;
    private String overview;
    private float popularity;
    private String youtubeTrailerKey;
    @SerializedName("backdrop_path")
    private String backdropImageKey;
    @SerializedName("poster_path")
    private String posterImageKey;
    private List<String> videosPath;


    public static final DiffUtil.ItemCallback<Movie> DIFF_CALL = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
           return  oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            return  oldItem.id == newItem.id;
        }
    };

    public Movie(int id, String originalTitle, List<String> genres) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImdbId() {
        return imdbId;
    }

    public void setImdbId(int imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirectorFullName() {
        return directorFullName;
    }

    public void setDirectorFullName(String directorFullName) {
        this.directorFullName = directorFullName;
    }

    public String getWriterFullName() {
        return writerFullName;
    }

    public void setWriterFullName(String writerFullName) {
        this.writerFullName = writerFullName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getYoutubeTrailerKey() {
        return youtubeTrailerKey;
    }

    public void setYoutubeTrailerKey(String youtubeTrailerKey) {
        this.youtubeTrailerKey = youtubeTrailerKey;
    }

    public String getBackdropImageKey() {
        return backdropImageKey;
    }

    public void setBackdropImageKey(String backdropImageKey) {
        this.backdropImageKey = backdropImageKey;
    }

    public String getPosterImageKey() {
        return posterImageKey;
    }

    public void setPosterImageKey(String posterImageKey) {
        this.posterImageKey = posterImageKey;
    }

    public List<String> getVideosPath() {
        return videosPath;
    }

    public void setVideosPath(List<String> videosPath) {
        this.videosPath = videosPath;
    }



}

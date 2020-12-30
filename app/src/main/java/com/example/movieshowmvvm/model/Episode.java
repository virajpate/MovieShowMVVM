package com.example.movieshowmvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("season")
    @Expose
    private int season;

    @SerializedName("episode")
    @Expose
    private int episode;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("air_date")
    @Expose
    private String airDate;


    public Integer getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }
}

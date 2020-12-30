package com.example.movieshowmvvm.Responses;

import com.example.movieshowmvvm.model.TvShowDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShowDetailsResponse {

    @SerializedName("tvShow")
    @Expose
    private TvShowDetails tvShow;


    public TvShowDetails getTvShow() {
        return tvShow;
    }
}

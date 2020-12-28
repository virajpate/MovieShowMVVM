package com.example.movieshowmvvm.Responses;

import com.example.movieshowmvvm.Model.TvShowModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponses {

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("pages")
    @Expose
    private int pages;

    @SerializedName("tv_shows")
    @Expose
    private List<TvShowModel> tvShows = null;



    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<TvShowModel> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TvShowModel> tvShows) {
        this.tvShows = tvShows;
    }
}

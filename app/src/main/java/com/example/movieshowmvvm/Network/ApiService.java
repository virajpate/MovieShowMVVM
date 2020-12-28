package com.example.movieshowmvvm.Network;

import com.example.movieshowmvvm.Responses.TvShowResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //get popolar moview show
    @GET("most-popular")
    Call<TvShowResponses> getMostPopularTvShow(@Query("page")int page);
}

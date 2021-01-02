package com.example.movieshowmvvm.Network;

import com.example.movieshowmvvm.Responses.TvShowDetailsResponse;
import com.example.movieshowmvvm.Responses.TvShowResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //get popolar moview show
    @GET("most-popular")
    Call<TvShowResponses> getMostPopularTvShow(@Query("page")int page);

    //get show details
    @GET("show-details")
    Call<TvShowDetailsResponse> getTvShowDetails(@Query("q")String tvShowId);

    //serch
    @GET("search")
    Call<TvShowResponses> searchTvShow(@Query("q")String query,
                                       @Query("page")int page);
}

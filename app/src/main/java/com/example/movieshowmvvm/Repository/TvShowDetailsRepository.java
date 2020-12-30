package com.example.movieshowmvvm.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieshowmvvm.Network.ApiClient;
import com.example.movieshowmvvm.Network.ApiService;
import com.example.movieshowmvvm.Responses.TvShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {

    private ApiService apiService;

    public TvShowDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowDetailsResponse> getShowDetailsData(String tvShowId){

        MutableLiveData<TvShowDetailsResponse> data=new MutableLiveData<>();

        apiService.getTvShowDetails(tvShowId).enqueue(new Callback<TvShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetailsResponse> call,@NonNull Response<TvShowDetailsResponse> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetailsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}

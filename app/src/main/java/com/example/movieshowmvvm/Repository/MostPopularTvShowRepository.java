package com.example.movieshowmvvm.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieshowmvvm.Network.ApiClient;
import com.example.movieshowmvvm.Network.ApiService;
import com.example.movieshowmvvm.Responses.TvShowResponses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowRepository {

    private ApiService apiService;

    public MostPopularTvShowRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowResponses> getMostPopularTvSHow(int page){
        MutableLiveData<TvShowResponses> data=new MutableLiveData<>();

        apiService.getMostPopularTvShow(page).enqueue(new Callback<TvShowResponses>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponses> call, @NonNull Response<TvShowResponses> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponses> call, @NonNull Throwable t) {

                data.setValue(null);
            }
        });

        return data;
    }
}

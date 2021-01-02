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

public class SearchTvShowRepository {

    private ApiService apiService;

    public SearchTvShowRepository() {
       apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowResponses> searchTvShow(String query,int page){

        MutableLiveData<TvShowResponses> data=new MutableLiveData<>();

        apiService.searchTvShow(query,page).enqueue(new Callback<TvShowResponses>() {
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

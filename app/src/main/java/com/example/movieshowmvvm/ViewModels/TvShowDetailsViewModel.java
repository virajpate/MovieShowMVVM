package com.example.movieshowmvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshowmvvm.Repository.TvShowDetailsRepository;
import com.example.movieshowmvvm.Responses.TvShowDetailsResponse;

public class TvShowDetailsViewModel extends ViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;

    public TvShowDetailsViewModel(){
        tvShowDetailsRepository=new TvShowDetailsRepository();
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId){

       return tvShowDetailsRepository.getShowDetailsData(tvShowId);
    }

}

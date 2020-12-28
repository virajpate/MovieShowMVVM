package com.example.movieshowmvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshowmvvm.Repository.MostPopularTvShowRepository;
import com.example.movieshowmvvm.Responses.TvShowResponses;

public class MostPopularTvShowViewModel extends ViewModel {

    private MostPopularTvShowRepository mostPopularTvShowRepository;

    public MostPopularTvShowViewModel(){
        mostPopularTvShowRepository=new MostPopularTvShowRepository();
    }

    public LiveData<TvShowResponses> getMostPopularTvShow(int page){

        return mostPopularTvShowRepository.getMostPopularTvSHow(page);
    }
}

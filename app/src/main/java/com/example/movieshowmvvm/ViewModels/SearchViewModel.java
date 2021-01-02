package com.example.movieshowmvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshowmvvm.Repository.SearchTvShowRepository;
import com.example.movieshowmvvm.Responses.TvShowResponses;

public class SearchViewModel extends ViewModel {

    private SearchTvShowRepository searchTvShowRepository;

    public SearchViewModel() {
        searchTvShowRepository=new SearchTvShowRepository();
    }

    public LiveData<TvShowResponses> searchTvShow(String query,int page){
        return searchTvShowRepository.searchTvShow(query,page);
    }
}

package com.example.movieshowmvvm.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieshowmvvm.Database.TvShowDatabase;
import com.example.movieshowmvvm.Repository.TvShowDetailsRepository;
import com.example.movieshowmvvm.Responses.TvShowDetailsResponse;
import com.example.movieshowmvvm.model.TvShowModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TvShowDetailsViewModel extends AndroidViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;
    private TvShowDatabase tvShowDatabase;

    public TvShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailsRepository=new TvShowDetailsRepository();
        tvShowDatabase=TvShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId){

       return tvShowDetailsRepository.getShowDetailsData(tvShowId);
    }

    public Completable addtoWatchlist(TvShowModel tvShowModel){
        return tvShowDatabase.tvShowDao().addToWatchlist(tvShowModel);
    }

    public Flowable<TvShowModel> getTvShowFromWatchlist(String tvshowid){
        return tvShowDatabase.tvShowDao().getTvShowFromWatchlist(tvshowid);
    }

    public Completable removeTvShowFromWatchList(TvShowModel tvShowModel){
        return tvShowDatabase.tvShowDao().removeFromWatchlist(tvShowModel);
    }

}

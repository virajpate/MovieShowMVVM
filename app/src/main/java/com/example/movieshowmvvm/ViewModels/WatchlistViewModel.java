package com.example.movieshowmvvm.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.movieshowmvvm.Database.TvShowDatabase;
import com.example.movieshowmvvm.model.TvShowModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {

    private TvShowDatabase tvShowDatabase;



    public WatchlistViewModel(@NonNull Application application) {
        super(application);

        tvShowDatabase=TvShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TvShowModel>> loadwatchlist(){
        return tvShowDatabase.tvShowDao().getWatchlist();
    }


    public Completable removeTvShowFromWatchlist(TvShowModel tvShowModel){
        return tvShowDatabase.tvShowDao().removeFromWatchlist(tvShowModel);
    }
}

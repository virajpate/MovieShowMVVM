package com.example.movieshowmvvm.Listner;

import com.example.movieshowmvvm.model.TvShowModel;

public interface WatchlistListner {

    void onTVshowClick(TvShowModel tvShowModel);

    void removeTvshowFromWatchlist(TvShowModel tvShowModel,int position);
}

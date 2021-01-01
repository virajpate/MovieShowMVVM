package com.example.movieshowmvvm.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshowmvvm.model.TvShowModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TvShowDao {

   @Query("SELECT * FROM TvShows")
    Flowable<List<TvShowModel>> getWatchlist();

   @Insert (onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchlist(TvShowModel tvShowModel);

   @Delete
    Completable removeFromWatchlist(TvShowModel tvShowModel);

   @Query("SELECT * FROM TvShows WHERE id=:tvshowid")
    Flowable<TvShowModel> getTvShowFromWatchlist(String tvshowid);
}

package com.example.movieshowmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.movieshowmvvm.Adapter.WatchListAdapter;
import com.example.movieshowmvvm.Listner.WatchlistListner;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.Utilities.TempDataHolder;
import com.example.movieshowmvvm.ViewModels.WatchlistViewModel;
import com.example.movieshowmvvm.databinding.ActivityWatchlistBinding;
import com.example.movieshowmvvm.model.TvShowModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity implements WatchlistListner {

    private ActivityWatchlistBinding activityWatchlistBinding;
    private WatchlistViewModel watchlistViewModel;
    private WatchListAdapter watchListAdapter;
    private List<TvShowModel> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchlistBinding= DataBindingUtil.setContentView(this,R.layout.activity_watchlist);

        Initialization();
    }

    private void Initialization() {

        //viewmodel
        watchlistViewModel=new ViewModelProvider(this).get(WatchlistViewModel.class);

        //back btn click
        activityWatchlistBinding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //tvlist
        watchlist=new ArrayList<>();

        //loadWatchlist
        loadwatchlis();
    }


    private void loadwatchlis(){

        activityWatchlistBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadwatchlist().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(tvShowModels -> {

            activityWatchlistBinding.setIsLoading(false);

            if (watchlist.size() >= 0){
                watchlist.clear();
            }

            watchlist.addAll(tvShowModels);

            watchListAdapter=new WatchListAdapter(watchlist,this);
            activityWatchlistBinding.WatchlistrecyclerView.setAdapter(watchListAdapter);
            activityWatchlistBinding.WatchlistrecyclerView.setVisibility(View.VISIBLE);
            compositeDisposable.dispose();

        }

        ));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (TempDataHolder.is_Watchlist_Updated){
            loadwatchlis();

            TempDataHolder.is_Watchlist_Updated =false;
        }


    }

    @Override
    public void onTVshowClick(TvShowModel tvShowModel) {

        Intent intent=new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShowModel);
        startActivity(intent);
    }

    @Override
    public void removeTvshowFromWatchlist(TvShowModel tvShowModel, int position) {

        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.removeTvShowFromWatchlist(tvShowModel)
                           .subscribeOn(Schedulers.computation())
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(()->{
                               watchlist.remove(position);
                               watchListAdapter.notifyItemRemoved(position);
                               watchListAdapter.notifyItemChanged(position,watchListAdapter.getItemCount());
                               compositeDisposable.dispose();
                           }));
    }
}

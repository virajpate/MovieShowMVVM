package com.example.movieshowmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.movieshowmvvm.Adapter.TvShowAdapter;
import com.example.movieshowmvvm.Listner.TvShowListner;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.Responses.TvShowResponses;
import com.example.movieshowmvvm.ViewModels.SearchViewModel;
import com.example.movieshowmvvm.databinding.ActivitySearchBinding;
import com.example.movieshowmvvm.model.TvShowModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TvShowListner {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel viewModel;
    private List<TvShowModel> tvShowModelList=new ArrayList<>();
    private TvShowAdapter tvShowAdapter;
    private int currentPage=1;
    private int totalAvailablepages=1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding= DataBindingUtil.setContentView(this,R.layout.activity_search);
        Initialization();

    }

    private void Initialization() {

        activitySearchBinding.backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activitySearchBinding.tvShowRecycleview.setHasFixedSize(true);

        viewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        tvShowAdapter=new TvShowAdapter(tvShowModelList,this);
        activitySearchBinding.tvShowRecycleview.setAdapter(tvShowAdapter);

        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (timer !=null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().trim().isEmpty()){
                    timer =new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                    currentPage=1;
                                    totalAvailablepages=1;
                                    searchTvShow(s.toString());
                                }
                            });

                        }
                    },800);
                }else {
                    tvShowModelList.clear();
                    tvShowAdapter.notifyDataSetChanged();
                }

            }
        });

        activitySearchBinding.tvShowRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!activitySearchBinding.tvShowRecycleview.canScrollVertically(1)){
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()){
                        if (currentPage < totalAvailablepages){
                            currentPage +=1;
                            searchTvShow(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });

        activitySearchBinding.inputSearch.requestFocus();

    }


    private void searchTvShow(String query){
        ToggleLoading();

        viewModel.searchTvShow(query,currentPage).observe(this, TvShowResponses->{
            ToggleLoading();

            if (TvShowResponses !=null){
                totalAvailablepages=TvShowResponses.getPages();

                if (TvShowResponses.getTvShows() !=null){
                    int oldcount=tvShowModelList.size();

                    tvShowModelList.addAll(TvShowResponses.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldcount,tvShowModelList.size());
                }
            }
        });

    }


    //progressbar show & hide
    private void ToggleLoading(){
        if (currentPage ==1){
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading())
            {
                activitySearchBinding.setIsLoading(false);
            }
            else
            {
                activitySearchBinding.setIsLoading(true);
            }
        }
        else
        {
            if (activitySearchBinding.getIsLoadingMore() !=null && activitySearchBinding.getIsLoadingMore())
            {
                activitySearchBinding.setIsLoadingMore(false);
            }
            else
            {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }


    @Override
    public void onTvShowClick(TvShowModel tvShowModel) {

        Intent intent = new Intent(getApplicationContext(), TvShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShowModel);
        startActivity(intent);
    }
}

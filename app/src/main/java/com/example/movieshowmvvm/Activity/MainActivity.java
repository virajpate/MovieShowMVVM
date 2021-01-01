package com.example.movieshowmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movieshowmvvm.Adapter.TvShowAdapter;
import com.example.movieshowmvvm.Listner.TvShowListner;
import com.example.movieshowmvvm.model.TvShowDetails;
import com.example.movieshowmvvm.model.TvShowModel;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.ViewModels.MostPopularTvShowViewModel;
import com.example.movieshowmvvm.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListner {

    private ActivityMainBinding activityMainBinding;
    private List<TvShowModel> tvShowModelList=new ArrayList<>();
    private TvShowAdapter tvShowAdapter;
    private MostPopularTvShowViewModel viewModel;
    private int currentPage=1;
    private int totalAvailabelPages=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        Initialization();


    }

    private void Initialization() {
        activityMainBinding.tvShowRecycleview.setHasFixedSize(true);
        //assign ViewModel
        viewModel=new ViewModelProvider(this).get(MostPopularTvShowViewModel.class);
        tvShowAdapter=new TvShowAdapter(tvShowModelList,this);
        activityMainBinding.tvShowRecycleview.setAdapter(tvShowAdapter);

        activityMainBinding.tvShowRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!activityMainBinding.tvShowRecycleview.canScrollVertically(1))
                {
                    if (currentPage <= totalAvailabelPages)
                    {
                        currentPage += 1;
                        getMostPopulartvShows();
                    }
                }
            }
        });


         //watchlist onClick Listner
        activityMainBinding.watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WatchlistActivity.class));
            }
        });

        //method
        getMostPopulartvShows();

    }


    private void getMostPopulartvShows() {

        ToggleLoading();

        viewModel.getMostPopularTvShow(currentPage).observe(this,TvShowResponses->{

            ToggleLoading();

            if (TvShowResponses!=null){
                if (TvShowResponses.getTvShows() !=null)
                {
                    totalAvailabelPages=TvShowResponses.getPages();
                    int oldCount=tvShowModelList.size();
                    tvShowModelList.addAll(TvShowResponses.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount,tvShowModelList.size());
                }
            }
        });



    }


    //progressbar show & hide
    private void ToggleLoading(){
        if (currentPage ==1){
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading())
            {
                activityMainBinding.setIsLoading(false);
            }
            else
            {
                activityMainBinding.setIsLoading(true);
            }
        }
        else
        {
            if (activityMainBinding.getIsLoadingMore() !=null && activityMainBinding.getIsLoadingMore())
            {
                activityMainBinding.setIsLoadingMore(false);
            }
            else
            {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClick(TvShowModel tvShowModel) {
        Intent intent = new Intent(getApplicationContext(), TvShowDetailsActivity.class);

        intent.putExtra("tvShow",tvShowModel);
//        intent.putExtra("id", tvShowModel.getId());
//        intent.putExtra("name", tvShowModel.getName());
//        intent.putExtra("startdate", tvShowModel.getStartDate());
//        intent.putExtra("country", tvShowModel.getCountry());
//        intent.putExtra("network", tvShowModel.getNetwork());
//        intent.putExtra("status", tvShowModel.getStatus());

        startActivity(intent);
    }
}

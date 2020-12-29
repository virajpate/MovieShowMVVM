package com.example.movieshowmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.movieshowmvvm.Adapter.TvShowAdapter;
import com.example.movieshowmvvm.model.TvShowModel;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.ViewModels.MostPopularTvShowViewModel;
import com.example.movieshowmvvm.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private List<TvShowModel> tvShowModelList=new ArrayList<>();
    private TvShowAdapter tvShowAdapter;
    private MostPopularTvShowViewModel viewModel;


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
        tvShowAdapter=new TvShowAdapter(tvShowModelList);
        activityMainBinding.tvShowRecycleview.setAdapter(tvShowAdapter);

        //method
        getMostPopulartvShows();

    }


    private void getMostPopulartvShows() {

        activityMainBinding.setIsLoading(true);

        viewModel.getMostPopularTvShow(0).observe(this,TvShowResponses->{

            activityMainBinding.setIsLoading(false);

            if (TvShowResponses!=null){
                if (TvShowResponses.getTvShows() !=null)
                {
                    tvShowModelList.addAll(TvShowResponses.getTvShows());
                    tvShowAdapter.notifyDataSetChanged();
                }
            }
        });



    }
}

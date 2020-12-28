package com.example.movieshowmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.Responses.TvShowResponses;
import com.example.movieshowmvvm.ViewModels.MostPopularTvShowViewModel;

public class MainActivity extends AppCompatActivity {

    private MostPopularTvShowViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign ViewModel
        viewModel=new ViewModelProvider(this).get(MostPopularTvShowViewModel.class);

        getMostPopulartvShows();

    }

    private void getMostPopulartvShows() {

        viewModel.getMostPopularTvShow(0).observe(this, TvShowResponses ->
                Toast.makeText(getApplicationContext(),"Total pages:-"+TvShowResponses.getPages(),Toast.LENGTH_SHORT).show());
    }
}

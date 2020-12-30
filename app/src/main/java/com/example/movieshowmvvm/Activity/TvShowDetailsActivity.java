package com.example.movieshowmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.movieshowmvvm.Adapter.ImageSliderAdapter;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.Responses.TvShowDetailsResponse;
import com.example.movieshowmvvm.ViewModels.TvShowDetailsViewModel;
import com.example.movieshowmvvm.databinding.ActivityTvShowDetailsBinding;

public class TvShowDetailsActivity extends AppCompatActivity {
    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);
        activityTvShowDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_tv_show_details);

        Initialization();
    }

    private void Initialization() {
        tvShowDetailsViewModel=new ViewModelProvider(this).get(TvShowDetailsViewModel.class);

        getTvShowDetails();
    }

    private void getTvShowDetails() {
        activityTvShowDetailsBinding.setIsLoading(true);

        String tvShowId=String.valueOf(getIntent().getIntExtra("id",-1));

        tvShowDetailsViewModel.getTvShowDetails(tvShowId).observe(this, TvShowDetailsResponse ->{

                    activityTvShowDetailsBinding.setIsLoading(true);
                  if (TvShowDetailsResponse.getTvShow() != null){
                      if (TvShowDetailsResponse.getTvShow().getPictures() !=null){
                          activityTvShowDetailsBinding.setIsLoading(false);
                          loadImageSlider(TvShowDetailsResponse.getTvShow().getPictures());
                      }

                  }
                }
                );
    }


    private void loadImageSlider(String[] slideimages){
        activityTvShowDetailsBinding.sliderviewpager.setOffscreenPageLimit(1);
        activityTvShowDetailsBinding.sliderviewpager.setAdapter(new ImageSliderAdapter(slideimages));
        activityTvShowDetailsBinding.sliderviewpager.setVisibility(View.VISIBLE);
        setUpSliderIndicator(slideimages.length);
        activityTvShowDetailsBinding.sliderviewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });

    }

    private void setUpSliderIndicator(int count){
        ImageView[] indicators=new ImageView[count];
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(8,0,8,0);

        for (int i=0;i<indicators.length;i++){
            indicators[i]=new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));

            indicators[i].setLayoutParams(layoutParams);
           activityTvShowDetailsBinding.layoutIndicator.addView(indicators[i]);
        }

        activityTvShowDetailsBinding.layoutIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }


    private void setCurrentSliderIndicator(int position){
        int childCount=activityTvShowDetailsBinding.layoutIndicator.getChildCount();
        for (int i=0;i<childCount; i++){
            ImageView imageView=(ImageView)activityTvShowDetailsBinding.layoutIndicator.getChildAt(i);
            if (i==position){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_active));
            }
            else
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));
            }
        }
    }
}

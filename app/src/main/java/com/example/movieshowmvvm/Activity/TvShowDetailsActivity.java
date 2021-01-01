package com.example.movieshowmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.movieshowmvvm.Adapter.EpisodeAdapter;
import com.example.movieshowmvvm.Adapter.ImageSliderAdapter;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.Responses.TvShowDetailsResponse;
import com.example.movieshowmvvm.Utilities.TempDataHolder;
import com.example.movieshowmvvm.ViewModels.TvShowDetailsViewModel;
import com.example.movieshowmvvm.databinding.ActivityTvShowDetailsBinding;
import com.example.movieshowmvvm.databinding.LayoutEpisodeBottomSheetBinding;
import com.example.movieshowmvvm.model.TvShowModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowDetailsActivity extends AppCompatActivity {
    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;
    private BottomSheetDialog bottomSheetDialog;
    private LayoutEpisodeBottomSheetBinding layoutEpisodeBottomSheetBinding;
    private TvShowModel tvShowModel;
    private Boolean istvshowAvailabelinWatchlist=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);
        activityTvShowDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_tv_show_details);

        Initialization();
    }

    private void Initialization() {
        tvShowDetailsViewModel=new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        activityTvShowDetailsBinding.imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvShowModel=(TvShowModel)getIntent().getSerializableExtra("tvShow");
        checkTvShowinWatchlist();
        getTvShowDetails();
    }

    private void getTvShowDetails() {
        activityTvShowDetailsBinding.setIsLoading(true);

        String tvShowId=String.valueOf(tvShowModel.getId());

        tvShowDetailsViewModel.getTvShowDetails(tvShowId).observe(this, TvShowDetailsResponse ->{

                    activityTvShowDetailsBinding.setIsLoading(true);
                     if (TvShowDetailsResponse.getTvShow() != null){
                      if (TvShowDetailsResponse.getTvShow().getPictures() !=null){
                          activityTvShowDetailsBinding.setIsLoading(false);
                          loadImageSlider(TvShowDetailsResponse.getTvShow().getPictures());
                      }

                      //imageview
                      activityTvShowDetailsBinding.setTvshowimageUrl(
                              TvShowDetailsResponse.getTvShow().getImagePath());
                      activityTvShowDetailsBinding.imageTvShow.setVisibility(View.VISIBLE);

                      //add description
                      activityTvShowDetailsBinding.setDescription(
                              String.valueOf(HtmlCompat.fromHtml(
                                      TvShowDetailsResponse.getTvShow().getDescription(),
                                      HtmlCompat.FROM_HTML_MODE_LEGACY))
                      );
                      activityTvShowDetailsBinding.textDescription.setEllipsize(null);

                      //ReadMore btn
                      activityTvShowDetailsBinding.Readmoretext.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              if (activityTvShowDetailsBinding.Readmoretext.getText().toString().equals("Read More"))
                              {
                                  activityTvShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                  activityTvShowDetailsBinding.textDescription.setEllipsize(null);
                                  activityTvShowDetailsBinding.Readmoretext.setText(R.string.readLess);
                              }
                              else
                              {
                                  activityTvShowDetailsBinding.textDescription.setMaxLines(4);
                                  activityTvShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                  activityTvShowDetailsBinding.Readmoretext.setText(R.string.readmore);
                              }
                          }
                      });

                      //get rating
                      activityTvShowDetailsBinding.setRating(
                              String.format(
                                      Locale.getDefault(),"%.2f",
                                      Double.parseDouble(TvShowDetailsResponse.getTvShow().getRating())
                              )
                      );

                      //get genre
                      if (TvShowDetailsResponse.getTvShow().getGenres() != null){
                          activityTvShowDetailsBinding.setGenre(TvShowDetailsResponse.getTvShow().getGenres()[0]);
                      }
                      else
                      {
                          activityTvShowDetailsBinding.setGenre("N/A");
                      }

                      //get Runtime
                      activityTvShowDetailsBinding.setRuntime(TvShowDetailsResponse.getTvShow().getRuntime()+ "Min");

                      //website btn click listner
                      activityTvShowDetailsBinding.btnWebsite.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              Intent i=new Intent(Intent.ACTION_VIEW);
                              i.setData(Uri.parse(TvShowDetailsResponse.getTvShow().getUrl()));
                              startActivity(i);
                          }
                      });

                      //episode btn click
                      activityTvShowDetailsBinding.btnEpisode.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              if (bottomSheetDialog == null){
                                  bottomSheetDialog =new BottomSheetDialog(TvShowDetailsActivity.this);

                                  layoutEpisodeBottomSheetBinding=DataBindingUtil.inflate(
                                          LayoutInflater.from(TvShowDetailsActivity.this),
                                          R.layout.layout_episode_bottom_sheet,
                                          findViewById(R.id.episodescontainer),false);

                                  bottomSheetDialog.setContentView(layoutEpisodeBottomSheetBinding.getRoot());

                                  layoutEpisodeBottomSheetBinding.episoderecycleview.setAdapter(new EpisodeAdapter(TvShowDetailsResponse.getTvShow().getEpisodes()));

                                  layoutEpisodeBottomSheetBinding.textTitle.setText(
                                          String.format("Episode |%s",getIntent().getStringExtra("name"))
                                  );

                                  //botomshit close image close btn
                                  layoutEpisodeBottomSheetBinding.imageClose.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          bottomSheetDialog.dismiss();
                                      }
                                  });

                              }

                              FrameLayout frameLayout=bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

                              if (frameLayout != null){
                                  BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from(frameLayout);
                                  bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                              }


                              bottomSheetDialog.show();

                          }
                      });


                      //add watchlist
                         activityTvShowDetailsBinding.watchlistImageview.setOnClickListener(v -> {

                             CompositeDisposable compositeDisposable=new CompositeDisposable();
                             if (istvshowAvailabelinWatchlist){

                                 compositeDisposable.add(tvShowDetailsViewModel.removeTvShowFromWatchList(tvShowModel)
                                                     .subscribeOn(Schedulers.computation())
                                                      .observeOn(AndroidSchedulers.mainThread())
                                                       .subscribe(()->{
                                                           istvshowAvailabelinWatchlist=false;
                                                           TempDataHolder.is_Watchlist_Updated =true;
                                                           activityTvShowDetailsBinding.watchlistImageview.setImageResource(R.drawable.ic_watch);
                                                           Toast.makeText(getApplicationContext(), "Remove from Watchlist", Toast.LENGTH_SHORT).show();
                                                           compositeDisposable.dispose();
                                                       }));

                             }
                             else {
                                 compositeDisposable.add(tvShowDetailsViewModel.addtoWatchlist(tvShowModel).subscribeOn(Schedulers.io())
                                         .observeOn(AndroidSchedulers.mainThread()).subscribe(()->{
                                             TempDataHolder.is_Watchlist_Updated =true;
                                             activityTvShowDetailsBinding.watchlistImageview.setImageResource(R.drawable.ic_check_black_24dp);
                                             Toast.makeText(getApplicationContext(),"Added watchlist",Toast.LENGTH_LONG).show();
                                             compositeDisposable.dispose();
                                         }));
                             }
                         });







                      loadBasicTvshowDetails();

                  }
                }
                );
    }

    private void checkTvShowinWatchlist(){
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(tvShowDetailsViewModel.getTvShowFromWatchlist(String.valueOf(tvShowModel.getId()))
                            .subscribeOn(Schedulers.computation())
                             .observeOn(AndroidSchedulers.mainThread())
                             .subscribe(tvShowModel ->{
                                 istvshowAvailabelinWatchlist=true;
                                 activityTvShowDetailsBinding.watchlistImageview.setImageResource(R.drawable.ic_check_black_24dp);
                                 compositeDisposable.dispose();
                             }));
    };

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


    private void loadBasicTvshowDetails(){
        activityTvShowDetailsBinding.setTvShowName(tvShowModel.getName());
        activityTvShowDetailsBinding.setNetworkcountry(tvShowModel.getNetwork()+"("+tvShowModel.getCountry()+")");
        activityTvShowDetailsBinding.setStatus(tvShowModel.getStatus());
        activityTvShowDetailsBinding.setStartDate(tvShowModel.getStartDate());

    }
}

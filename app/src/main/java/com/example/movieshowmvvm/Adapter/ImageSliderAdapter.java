package com.example.movieshowmvvm.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.databinding.ItemContainerSliderImageBinding;
import com.example.movieshowmvvm.databinding.ItemContainerTvShowBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{

    private String[] sliderimages;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderimages) {
        this.sliderimages = sliderimages;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater ==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding SliderImageBinding= DataBindingUtil.inflate(layoutInflater,R.layout.item_container_slider_image,parent,false);
        return new ImageSliderViewHolder(SliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSlideImage(sliderimages[position]);

    }

    @Override
    public int getItemCount() {
        return sliderimages.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder{
        private ItemContainerSliderImageBinding itemContainerSliderImageBinding;

        public ImageSliderViewHolder(ItemContainerSliderImageBinding itemContainerSliderImageBinding){
            super(itemContainerSliderImageBinding.getRoot());
            this.itemContainerSliderImageBinding=itemContainerSliderImageBinding;
        }


        public void bindSlideImage(String imageUrl){
            itemContainerSliderImageBinding.setImageUrl(imageUrl);
        }

    }
}

package com.example.movieshowmvvm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshowmvvm.Listner.TvShowListner;
import com.example.movieshowmvvm.Listner.WatchlistListner;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.databinding.ItemContainerTvShowBinding;
import com.example.movieshowmvvm.model.TvShowModel;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.TvShowViewHolder>{

    private List<TvShowModel> tvShowModelList;
    private LayoutInflater layoutInflater;
    private WatchlistListner watchlistListner;

    public WatchListAdapter(List<TvShowModel> tvShowModelList, WatchlistListner watchlistListner) {
        this.tvShowModelList = tvShowModelList;
        this.watchlistListner= watchlistListner;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater ==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding TvShowBinding= DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show,parent,false);

        return new TvShowViewHolder(TvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bindTvShow(tvShowModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShowModelList.size();
    }


     class TvShowViewHolder extends RecyclerView.ViewHolder{

        //databinding of itemTvshow
       private ItemContainerTvShowBinding itemContainerTvShowBinding;

       //construcor
        public TvShowViewHolder(ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding=itemContainerTvShowBinding;
        }

        public void bindTvShow(TvShowModel tvShowModel){
            itemContainerTvShowBinding.setTvShow(tvShowModel);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchlistListner.onTVshowClick(tvShowModel);
                }
            });

            itemContainerTvShowBinding.imageDeletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchlistListner.removeTvshowFromWatchlist(tvShowModel,getAdapterPosition());
                }
            });

            itemContainerTvShowBinding.imageDeletebtn.setVisibility(View.VISIBLE);

        }

    }
}

package com.example.movieshowmvvm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshowmvvm.Listner.TvShowListner;
import com.example.movieshowmvvm.model.TvShowModel;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.databinding.ItemContainerTvShowBinding;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>{

    private List<TvShowModel> tvShowModelList;
    private LayoutInflater layoutInflater;
    private TvShowListner tvShowListner;

    public TvShowAdapter(List<TvShowModel> tvShowModelList,TvShowListner tvShowListner) {
        this.tvShowModelList = tvShowModelList;
        this.tvShowListner= tvShowListner;
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
                    tvShowListner.onTvShowClick(tvShowModel);
                }
            });

        }

    }
}

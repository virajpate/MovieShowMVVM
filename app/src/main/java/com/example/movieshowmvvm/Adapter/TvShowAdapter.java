package com.example.movieshowmvvm.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshowmvvm.model.TvShowModel;
import com.example.movieshowmvvm.R;
import com.example.movieshowmvvm.databinding.ItemContainerTvShowBinding;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>{

    private List<TvShowModel> tvShowModelList;
    private LayoutInflater layoutInflater;

    public TvShowAdapter(List<TvShowModel> tvShowModelList) {
        this.tvShowModelList = tvShowModelList;
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

    static class TvShowViewHolder extends RecyclerView.ViewHolder{

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

        }

    }
}

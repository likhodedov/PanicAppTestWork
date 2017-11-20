package com.withyou.apps.panicapp.VacancyDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.RequestOptions;
import com.withyou.apps.panicapp.R;
import com.withyou.apps.panicapp.VacancyData.CompanyPhoto;

import java.util.List;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.MyViewHolder> {
    private List<CompanyPhoto> PhotoList;
    private final Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
        }
    }
    public PhotoRecyclerAdapter(List<CompanyPhoto> list, Context context) {
        this.PhotoList = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_photo_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CompanyPhoto model = PhotoList.get(position);
        Glide.with(context)
                .load(model.getUrl())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.placeholderOf(R.drawable.loading_photo_preview))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return PhotoList.size();
    }
}

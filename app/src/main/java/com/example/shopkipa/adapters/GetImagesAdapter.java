package com.example.shopkipa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.GetItemImagesModel;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

public class GetImagesAdapter extends RecyclerView.Adapter<GetImagesAdapter.GetImagesViewHolder> {

    private final Context mContext;
    private final ArrayList<GetItemImagesModel> mImagesArrayList;
    private final LayoutInflater mLayoutInflator;

    public GetImagesAdapter(Context context, ArrayList<GetItemImagesModel>imagesArray){
        mContext = context;
        mImagesArrayList = imagesArray;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public GetImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.image_view_pager,parent,false);
        return new GetImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetImagesViewHolder holder, int position) {
        GetItemImagesModel getItemImagesModel = mImagesArrayList.get(position);
        Glide.with(mContext).load(Constants.BASE_URL+"images/"+getItemImagesModel.getImageurl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImagesArrayList.size();
    }

    public class GetImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public GetImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemimage);
        }
    }
}

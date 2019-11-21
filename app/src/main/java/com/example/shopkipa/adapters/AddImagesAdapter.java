package com.example.shopkipa.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.ImagesModel;

import java.util.ArrayList;

public class AddImagesAdapter extends RecyclerView.Adapter<AddImagesAdapter.AddImagesViewHolders> {
    private final Context mContext;
    private final ArrayList<ImagesModel> mImagesArray;
    private final LayoutInflater mLayoutInflator;

    public AddImagesAdapter(Context context, ArrayList<ImagesModel>imagesArray){
        mContext = context;
        mImagesArray = imagesArray;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public AddImagesViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.add_images,parent,false);
        return new AddImagesViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddImagesViewHolders holder, int position) {
        ImagesModel imagesModel = mImagesArray.get(position);
        Glide.with(mContext)
                .load(Uri.parse(imagesModel.getImage()))
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return mImagesArray.size();
    }

    public class AddImagesViewHolders extends RecyclerView.ViewHolder {
        ImageView images;
        public AddImagesViewHolders(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.productImage);
        }
    }
}

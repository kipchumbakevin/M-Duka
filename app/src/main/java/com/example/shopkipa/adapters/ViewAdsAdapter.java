package com.example.shopkipa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.ViewAdsModel;
import com.example.shopkipa.ui.MainActivity;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;
public class ViewAdsAdapter extends RecyclerView.Adapter<ViewAdsAdapter.ViewAdsViewHolder> {
    private final Context mContext;
    private final ArrayList<ViewAdsModel> mAdsArray;
    private final LayoutInflater mLayoutInflator;

    public ViewAdsAdapter(Context context, ArrayList<ViewAdsModel>adsModels){
        mContext = context;
        mAdsArray = adsModels;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ViewAdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.adsview,parent,false);
        return new ViewAdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdsViewHolder holder, int position) {
        ViewAdsModel viewAdsModel = mAdsArray.get(position);
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+viewAdsModel.getImageurl())
                .into(holder.adimage);
        holder.mcurrentposition = viewAdsModel.getId();
    }

    @Override
    public int getItemCount() {
        return mAdsArray.size();
    }

    public class ViewAdsViewHolder extends RecyclerView.ViewHolder {
        ImageView adimage,cancelad;
        int mcurrentposition;
        public ViewAdsViewHolder(@NonNull View itemView) {
            super(itemView);
            adimage = itemView.findViewById(R.id.adimage);
            cancelad = itemView.findViewById(R.id.cancelad);

            cancelad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdsArray.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),mAdsArray.size());
                }
            });
            adimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Goes to company website",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

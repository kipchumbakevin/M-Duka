package com.example.shopkipa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.ViewGivenStockModel;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

public class GivenStockAdapter extends RecyclerView.Adapter<GivenStockAdapter.GivenViewHolders> {
    private final Context mContext;
    private final ArrayList<ViewGivenStockModel> mGivenArrayList;
    private final LayoutInflater mLayoutInflator;

    public GivenStockAdapter(Context context, ArrayList<ViewGivenStockModel>givenStockModels){
        mContext = context;
        mGivenArrayList = givenStockModels;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public GivenViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.givenstockview,parent,false);
        return new GivenViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GivenViewHolders holder, int position) {
        ViewGivenStockModel viewGivenStockModel = mGivenArrayList.get(position);
        holder.headername.setText(viewGivenStockModel.getName());
        holder.color.setText(viewGivenStockModel.getColor());
        holder.itemname.setText(viewGivenStockModel.getName());
        holder.size.setText(viewGivenStockModel.getSize());
        holder.qq = viewGivenStockModel.getGivenQuantity();
        holder.qqq = Integer.toString(holder.qq);
        holder.quantity.setText(holder.qqq);
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+viewGivenStockModel.getImage())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mGivenArrayList.size();
    }

    public class GivenViewHolders extends RecyclerView.ViewHolder {
        TextView headername,color,itemname,size,quantity;
        ImageView clear,image;
        int qq;
        String qqq;
        public GivenViewHolders(@NonNull View itemView) {
            super(itemView);
            headername = itemView.findViewById(R.id.gheader);
            color = itemView.findViewById(R.id.gheader_color);
            itemname = itemView.findViewById(R.id.gitemname);
            size = itemView.findViewById(R.id.gitemsize);
            quantity = itemView.findViewById(R.id.gitemquantity);
            clear = itemView.findViewById(R.id.cleargiven);
            image = itemView.findViewById(R.id.gimage);
        }
    }
}

package com.example.shopkipa.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

public class ItemsInTypeAdapter extends RecyclerView.Adapter<ItemsInTypeAdapter.ItemsViewHolder> {

    private final Context mContext;
    private final ArrayList<GetStockInTypeModel> mStockArrayList;
    private final LayoutInflater mLayoutInflator;

    public ItemsInTypeAdapter(Context context, ArrayList<GetStockInTypeModel>stockArrayList){
        mContext = context;
        mStockArrayList = stockArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.items_layout,parent,false);
        Log.d("Fetch", "Reached here too");

        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Log.d("Fetch", "Reached here too");

        GetStockInTypeModel getStockInTypeModel = mStockArrayList.get(position);
        holder.itemname.setText(getStockInTypeModel.getName());
        Glide.with(mContext)
                .load(Constants.BASE_URL+"images/"+getStockInTypeModel.getImage())
                .into(holder.itemImage);
        holder.itemquantity.setText(getStockInTypeModel.getQuantity());
        holder.itemsize.setText(getStockInTypeModel.getSize());

    }

    @Override
    public int getItemCount() {
        return mStockArrayList.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemname,itemsize,itemquantity,moreDetails;
        ImageView itemImage,deleteItem;
        Button sold,edit;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            itemsize = itemView.findViewById(R.id.itemsize);
            itemquantity = itemView.findViewById(R.id.itemquantity);
            itemImage = itemView.findViewById(R.id.itemimage);
            deleteItem = itemView.findViewById(R.id.deleteProduct);
            sold = itemView.findViewById(R.id.soldproduct);
            edit = itemView.findViewById(R.id.editProduct);
            moreDetails = itemView.findViewById(R.id.more_details);
        }
    }
}

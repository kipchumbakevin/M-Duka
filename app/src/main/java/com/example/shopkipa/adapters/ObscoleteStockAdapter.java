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
import com.example.shopkipa.models.ViewObscoleteStockModel;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

public class ObscoleteStockAdapter extends RecyclerView.Adapter<ObscoleteStockAdapter.ObscoleteViewHolders> {
    private final Context mContext;
    private final ArrayList<ViewObscoleteStockModel> mObscoleteArrayList;
    private final LayoutInflater mLayoutInflator;

    public ObscoleteStockAdapter(Context context, ArrayList<ViewObscoleteStockModel>obscoleteStockModels){
        mContext = context;
        mObscoleteArrayList = obscoleteStockModels;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ObscoleteViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.obscoletestockview,parent,false);
        return new ObscoleteViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObscoleteViewHolders holder, int position) {
        ViewObscoleteStockModel viewObscoleteStockModel = mObscoleteArrayList.get(position);
        holder.headername.setText(viewObscoleteStockModel.getName());
        holder.color.setText(viewObscoleteStockModel.getColor());
        holder.itemname.setText(viewObscoleteStockModel.getName());
        holder.size.setText(viewObscoleteStockModel.getSize());
        holder.qq = viewObscoleteStockModel.getObscoleteQuantity();
        holder.qqq = Integer.toString(holder.qq);
        holder.quantity.setText(holder.qqq);
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+viewObscoleteStockModel.getImage())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mObscoleteArrayList.size();
    }

    public class ObscoleteViewHolders extends RecyclerView.ViewHolder {
        TextView headername,color,itemname,size,quantity;
        ImageView clear,image;
        int qq;
        String qqq;
        public ObscoleteViewHolders(@NonNull View itemView) {
            super(itemView);
            headername = itemView.findViewById(R.id.oheader);
            color = itemView.findViewById(R.id.oheader_color);
            itemname = itemView.findViewById(R.id.oitemname);
            size = itemView.findViewById(R.id.oitemsize);
            quantity = itemView.findViewById(R.id.oitemquantity);
            clear = itemView.findViewById(R.id.clearobscolete);
            image = itemView.findViewById(R.id.oimage);
        }
    }
}

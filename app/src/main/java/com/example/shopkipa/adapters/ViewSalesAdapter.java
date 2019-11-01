package com.example.shopkipa.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetSalesInMonthModel;

import java.util.ArrayList;

public class ViewSalesAdapter extends RecyclerView.Adapter<ViewSalesAdapter.ViewSalesHolder> {

    private final Context mContext;
    private final ArrayList<GetSalesInMonthModel> mSalesArrayList;
    private final LayoutInflater mLayoutInflator;

    public ViewSalesAdapter(Context context, ArrayList<GetSalesInMonthModel>salesArrayList){
        mContext = context;
        mSalesArrayList = salesArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ViewSalesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.allsalesview,parent,false);
        return new ViewSalesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSalesHolder holder, int position) {
        GetSalesInMonthModel getSalesInMonthModel = mSalesArrayList.get(position);
        holder.itemName.setText(getSalesInMonthModel.getName());
        holder.totalAmount.setText(getSalesInMonthModel.getTotal());
        holder.quantitySold.setText(getSalesInMonthModel.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mSalesArrayList.size();
    }

    public class ViewSalesHolder extends RecyclerView.ViewHolder {
        TextView itemName,quantitySold,totalAmount;
        ConstraintLayout select;
        ImageView deleteSale;
        public ViewSalesHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            quantitySold = itemView.findViewById(R.id.quantitySold);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            select = itemView.findViewById(R.id.selectItemsToDelete);
            deleteSale = itemView.findViewById(R.id.deleteSale);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!deleteSale.isShown()){
                        deleteSale.setVisibility(View.VISIBLE);
                    }else{
                        deleteSale.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}

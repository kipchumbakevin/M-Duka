package com.example.shopkipa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetSalesModel;

import java.util.ArrayList;

public class GetSalesAdapter extends RecyclerView.Adapter<GetSalesAdapter.GetSaleViewHolder> {

    private final Context mContext;
    private final ArrayList<GetSalesModel> mSalesArrayList;
    private final LayoutInflater mLayoutInflator;

    public GetSalesAdapter(Context context, ArrayList<GetSalesModel>salesArrayList){
        mContext = context;
        mSalesArrayList = salesArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public GetSaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.summary,parent,false);
        return new GetSaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetSaleViewHolder holder, int position) {
        GetSalesModel getSalesModel = mSalesArrayList.get(position);
        holder.rawProfit.setText("Kshs." + getSalesModel.getTotalProfit());
        holder.expenses.setText("Kshs." + getSalesModel.getTotalExpense());
        int total = (getSalesModel.getTotalProfit() - getSalesModel.getTotalExpense());
        if (total>0){
            holder.profit_loss.setText("Profit");
            holder.totalprofit.setText("Ksh." + (getSalesModel.getTotalProfit() - getSalesModel.getTotalExpense()));
        }else if (total<0){
            holder.profit_loss.setText("Loss");
            holder.totalprofit.setText("Ksh." + (getSalesModel.getTotalExpense()-getSalesModel.getTotalProfit()));
        }

    }

    @Override
    public int getItemCount() {
        return mSalesArrayList.size();
    }

    public class GetSaleViewHolder extends RecyclerView.ViewHolder {
        TextView rawProfit,expenses,totalprofit,profit_loss;

        public GetSaleViewHolder(@NonNull View itemView) {
            super(itemView);
            rawProfit = itemView.findViewById(R.id.rawProfit);
            expenses = itemView.findViewById(R.id.expenses);
            totalprofit = itemView.findViewById(R.id.totalProfit);
            profit_loss = itemView.findViewById(R.id.profit_loss);

        }
    }
}

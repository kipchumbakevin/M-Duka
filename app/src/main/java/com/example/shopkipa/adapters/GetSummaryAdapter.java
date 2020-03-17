package com.example.shopkipa.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetSummaryModel;

import java.util.ArrayList;

public class GetSummaryAdapter extends RecyclerView.Adapter<GetSummaryAdapter.GetSummaryViewHolder> {

    private final Context mContext;
    private final ArrayList<GetSummaryModel> mSummaryArrayList;
    private final LayoutInflater mLayoutInflator;

    public GetSummaryAdapter(Context context, ArrayList<GetSummaryModel> summaryArrayList) {
        mContext = context;
        mSummaryArrayList = summaryArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public GetSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.summary, parent, false);
        return new GetSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetSummaryViewHolder holder, int position) {
        GetSummaryModel getSummaryModel = mSummaryArrayList.get(position);
        Log.i("conn", " " + getSummaryModel.getTotalProfit());
        holder.rawProfit.setText("Kshs." + getSummaryModel.getTotalProfit());
        holder.expenses.setText("Kshs." + getSummaryModel.getTotalExpense());
        int total = (getSummaryModel.getTotalProfit() - getSummaryModel.getTotalExpense());
        if (total >= 0) {
            holder.profit_loss.setText("Profit");
            holder.totalprofit.setText("Ksh." + (getSummaryModel.getTotalProfit() - getSummaryModel.getTotalExpense()));
        } else {
            holder.profit_loss.setText("Loss");
            holder.totalprofit.setText("Ksh." + (getSummaryModel.getTotalExpense() - getSummaryModel.getTotalProfit()));
        }

    }

    @Override
    public int getItemCount() {
        return mSummaryArrayList.size();
    }

    public class GetSummaryViewHolder extends RecyclerView.ViewHolder {
        TextView rawProfit, expenses, totalprofit, profit_loss;

        public GetSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            rawProfit = itemView.findViewById(R.id.rawProfit);
            expenses = itemView.findViewById(R.id.expensesTotal);
            totalprofit = itemView.findViewById(R.id.totalProfit);
            profit_loss = itemView.findViewById(R.id.profit_loss);
        }
    }
}

package com.example.shopkipa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetExpenseModel;

import java.util.ArrayList;

public class ViewExpensesAdapter extends RecyclerView.Adapter<ViewExpensesAdapter.ViewExpenseHolder> {

    private final Context mContext;
    private final ArrayList<GetExpenseModel> mExpensesArrayList;
    private final LayoutInflater mLayoutInflator;

    public ViewExpensesAdapter(Context context, ArrayList<GetExpenseModel>expenseArrayList){
        mContext = context;
        mExpensesArrayList = expenseArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);

    }
    @NonNull
    @Override
    public ViewExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.allexpenseview,parent,false);
        return new ViewExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewExpenseHolder holder, int position) {
        GetExpenseModel getExpenseModel = mExpensesArrayList.get(position);
        holder.expenseType.setText(getExpenseModel.getComment());
        holder.expenseAmount.setText("Kshs." + getExpenseModel.getAmount());

    }

    @Override
    public int getItemCount() {
        return mExpensesArrayList.size();
    }

    public class ViewExpenseHolder extends RecyclerView.ViewHolder {
        TextView expenseAmount,expenseType;
        public ViewExpenseHolder(@NonNull View itemView) {
            super(itemView);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
            expenseType = itemView.findViewById(R.id.expenseType);
        }
    }
}

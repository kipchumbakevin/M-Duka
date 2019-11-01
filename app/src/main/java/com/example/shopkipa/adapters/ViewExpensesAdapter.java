package com.example.shopkipa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.MainActivity;
import com.example.shopkipa.R;
import com.example.shopkipa.SummaryActivity;
import com.example.shopkipa.models.DeleteExpenseModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.id = mExpensesArrayList.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return mExpensesArrayList.size();
    }

    public class ViewExpenseHolder extends RecyclerView.ViewHolder {
        TextView expenseAmount,expenseType;
        ImageView deleteExpense;
        int id;
        ConstraintLayout select;
        public ViewExpenseHolder(@NonNull View itemView) {
            super(itemView);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
            expenseType = itemView.findViewById(R.id.expenseType);
            select = itemView.findViewById(R.id.selectItemsToDelete);
            deleteExpense = itemView.findViewById(R.id.deleteExpense);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!deleteExpense.isShown()){
                        deleteExpense.setVisibility(View.VISIBLE);
                    }else{
                        deleteExpense.setVisibility(View.GONE);
                    }
                }
            });
            deleteExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                        String iD = Integer.toString(id);
                        Call<DeleteExpenseModel> call = RetrofitClient.getInstance(mContext)
                                .getApiConnector()
                                .deleteExpense(iD);
                        call.enqueue(new Callback<DeleteExpenseModel>() {
                            @Override
                            public void onResponse(Call<DeleteExpenseModel> call, Response<DeleteExpenseModel> response) {
                                if(response.code()==201){
                                    Intent intent = new Intent(mContext, SummaryActivity.class);
                                    mContext.startActivity(intent);
                                    ((Activity)mContext).finish();
                                    Toast.makeText(mContext,"Deleted successfully",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(mContext,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<DeleteExpenseModel> call, Throwable t) {
                                Toast.makeText(mContext,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
            });
        }
    }
}

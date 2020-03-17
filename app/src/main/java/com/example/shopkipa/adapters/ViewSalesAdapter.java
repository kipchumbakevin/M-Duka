package com.example.shopkipa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.DeleteSaleModel;
import com.example.shopkipa.models.GetSalesInMonthModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.SummaryActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSalesAdapter extends RecyclerView.Adapter<ViewSalesAdapter.ViewSalesHolder> {

    private final Context mContext;
    private final ArrayList<GetSalesInMonthModel> mSalesArrayList;
    private final LayoutInflater mLayoutInflator;
    RelativeLayout progress;

    public ViewSalesAdapter(Context context, ArrayList<GetSalesInMonthModel> salesArrayList) {
        mContext = context;
        mSalesArrayList = salesArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewSalesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.allsalesview, parent, false);
        return new ViewSalesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSalesHolder holder, int position) {
        GetSalesInMonthModel getSalesInMonthModel = mSalesArrayList.get(position);
        holder.itemName.setText(getSalesInMonthModel.getName());
        holder.totalAmount.setText("Kshs." + getSalesInMonthModel.getTotal());
        holder.quantitySold.setText(getSalesInMonthModel.getQuantity());
        holder.colorC.setText(getSalesInMonthModel.getColor());
        holder.sizeS.setText(getSalesInMonthModel.getSize());
        holder.id = mSalesArrayList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mSalesArrayList.size();
    }

    public class ViewSalesHolder extends RecyclerView.ViewHolder {
        TextView itemName, quantitySold, totalAmount, sizeS, colorC;
        ConstraintLayout select;
        LinearLayoutCompat salesInfo;
        int id;
        ImageView deleteSale, moreInfo;

        public ViewSalesHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            quantitySold = itemView.findViewById(R.id.quantitySold);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            select = itemView.findViewById(R.id.selectItemsToDelete);
            deleteSale = itemView.findViewById(R.id.deleteSale);
            sizeS = itemView.findViewById(R.id.size);
            colorC = itemView.findViewById(R.id.color);
            moreInfo = itemView.findViewById(R.id.moreInfo);
            salesInfo = itemView.findViewById(R.id.salesDetails);
            progress = itemView.findViewById(R.id.progressLoad);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!deleteSale.isShown()) {
                        deleteSale.setVisibility(View.VISIBLE);
                    } else {
                        deleteSale.setVisibility(View.GONE);
                    }
                }
            });

            deleteSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete();
                }
            });
            moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    info();
                }
            });
        }

        private void info() {
            if (itemName.isShown()) {
                salesInfo.setVisibility(View.VISIBLE);
                itemName.setVisibility(View.GONE);
                quantitySold.setVisibility(View.GONE);
                totalAmount.setVisibility(View.GONE);


            } else {
                salesInfo.setVisibility(View.GONE);
                itemName.setVisibility(View.VISIBLE);
                quantitySold.setVisibility(View.VISIBLE);
                totalAmount.setVisibility(View.VISIBLE);
            }
        }

        private void delete() {
            showProgress();
            String iD = Integer.toString(id);
            Call<DeleteSaleModel> call = RetrofitClient.getInstance(mContext)
                    .getApiConnector()
                    .deleteSale(iD);
            call.enqueue(new Callback<DeleteSaleModel>() {
                @Override
                public void onResponse(Call<DeleteSaleModel> call, Response<DeleteSaleModel> response) {
                    hideProgress();
                    if (response.code() == 201) {
                        Intent intent = new Intent(mContext, SummaryActivity.class);
                        mContext.startActivity(intent);
                        ((Activity) mContext).finish();
                        Toast.makeText(mContext, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "response:" + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<DeleteSaleModel> call, Throwable t) {
                    hideProgress();
                    Toast.makeText(mContext, "errot:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void showProgress() {
            progress.setVisibility(View.VISIBLE);
        }

        private void hideProgress() {
            progress.setVisibility(View.GONE);
        }
    }
}

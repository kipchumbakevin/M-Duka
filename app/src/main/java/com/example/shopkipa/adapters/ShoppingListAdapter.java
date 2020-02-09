package com.example.shopkipa.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.DeleteItemModel;
import com.example.shopkipa.models.ViewShoppingListModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.ShoppingListActivity;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolders> {
    private final Context mContext;
    private final ArrayList<ViewShoppingListModel> mShoppingListArray;
    private final LayoutInflater mLayoutInflator;

    public ShoppingListAdapter(Context context, ArrayList<ViewShoppingListModel>viewShoppingListModels){
        mContext = context;
        mShoppingListArray = viewShoppingListModels;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ShoppingListViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.shoppinglistview,parent,false);
        return new ShoppingListViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolders holder, int position) {
        ViewShoppingListModel viewShoppingListModel = mShoppingListArray.get(position);
        holder.sname.setText(viewShoppingListModel.getName());
        holder.scolor.setText(viewShoppingListModel.getColor());
        holder.sitemsize.setText(viewShoppingListModel.getSize());
        holder.sitemname.setText(viewShoppingListModel.getName());
        holder.qq = mShoppingListArray.get(position).getShoppingQuantity();
        holder.qqq = Integer.toString(holder.qq);
        holder.itemId = mShoppingListArray.get(position).getId();
        holder.sitemquantity.setText(holder.qqq);
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+viewShoppingListModel.getImage())
                .into(holder.simage);
    }

    @Override
    public int getItemCount() {
        return mShoppingListArray.size();
    }

    public class ShoppingListViewHolders extends RecyclerView.ViewHolder {
        TextView sname,scolor,sitemsize,sitemname,sitemquantity;
        ImageView arrowdown,arrowup,simage,sclear;
        String qqq;
        int itemId;
        int qq;

        public ShoppingListViewHolders(@NonNull View itemView) {
            super(itemView);
            sname = itemView.findViewById(R.id.sheader);
            scolor = itemView.findViewById(R.id.sheader_color);
            sitemsize =  itemView.findViewById(R.id.sitemsize);
            sitemname = itemView.findViewById(R.id.sitemname);
            arrowdown = itemView.findViewById(R.id.arrowDown);
            arrowup = itemView.findViewById(R.id.arrowUp);
            simage = itemView.findViewById(R.id.simage);
            sclear = itemView.findViewById(R.id.sclearshopping);
            sitemquantity = itemView.findViewById(R.id.sitemquantity);

            sclear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to remove from shopping list?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    final String item_id = Integer.toString(itemId);

                                    Call<DeleteItemModel> call = RetrofitClient.getInstance(mContext)
                                            .getApiConnector()
                                            .deleteStock(item_id);
                                    call.enqueue(new Callback<DeleteItemModel>() {
                                        @Override
                                        public void onResponse(Call<DeleteItemModel> call, Response<DeleteItemModel> response) {
                                            if (response.code() == 201) {
                                                Intent intent = new Intent(mContext, ShoppingListActivity.class);
                                                mContext.startActivity(intent);
                                                Toast.makeText(mContext, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mContext, "response:" + " "+ item_id+" "+response.message(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<DeleteItemModel> call, Throwable t) {
                                            Toast.makeText(mContext, "errot:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }
}

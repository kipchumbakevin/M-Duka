package com.example.shopkipa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.DeleteItemModel;
import com.example.shopkipa.models.EditQuantityModel;
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
        holder.header.setText(viewShoppingListModel.getName());
        holder.headerColor.setText("("+viewShoppingListModel.getColor()+")");
        holder.sitemsize.setText(viewShoppingListModel.getSize());
        holder.sitemname.setText(viewShoppingListModel.getName());
        holder.qq = mShoppingListArray.get(position).getShoppingQuantity();
        holder.qqq = Integer.toString(holder.qq);
        holder.itemId = mShoppingListArray.get(position).getShoppingId();
        holder.sitemquantity.setText(holder.qqq);
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+viewShoppingListModel.getImage())
                .into(holder.simage);
    }

    @Override
    public int getItemCount() {
        return mShoppingListArray.size();
    }

    public class ShoppingListViewHolders extends RecyclerView.ViewHolder {
        TextView scolor,sitemsize,sitemname,sitemquantity,headerSize,headerColor,header;
        ImageView arrowDown,arrowUp,simage,sclear;
        String qqq;
        Button edit;
        int itemId,qq;
        LinearLayoutCompat fulldetails;
        RelativeLayout dropdown;

        public ShoppingListViewHolders(@NonNull View itemView) {
            super(itemView);
            sitemsize =  itemView.findViewById(R.id.sitemsize);
            sitemname = itemView.findViewById(R.id.sitemname);
            edit = itemView.findViewById(R.id.edit_shopping);
            simage = itemView.findViewById(R.id.simage);
            sclear = itemView.findViewById(R.id.sclearshopping);
            sitemquantity = itemView.findViewById(R.id.sitemquantity);
            arrowDown = itemView.findViewById(R.id.arrowDown);
            arrowUp = itemView.findViewById(R.id.arrowUp);
            headerSize = itemView.findViewById(R.id.header_size);
            headerColor = itemView.findViewById(R.id.sheader_color);
            header = itemView.findViewById(R.id.sheader);
            fulldetails = itemView.findViewById(R.id.fulldetails);
            dropdown = itemView.findViewById(R.id.dropDown);

            dropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!fulldetails.isShown()) {
                        header.setVisibility(View.GONE);
                        headerColor.setVisibility(View.GONE);
                       // headerSize.setVisibility(View.GONE);
                        simage.setVisibility(View.VISIBLE);
                        fulldetails.setVisibility(View.VISIBLE);
                        arrowUp.setVisibility(View.VISIBLE);
                        arrowDown.setVisibility(View.GONE);
                    } else {
                        fulldetails.setVisibility(View.GONE);
                        simage.setVisibility(View.GONE);
                        header.setVisibility(View.VISIBLE);
                       // headerSize.setVisibility(View.VISIBLE);
                        headerColor.setVisibility(View.VISIBLE);
                        arrowDown.setVisibility(View.VISIBLE);
                        arrowUp.setVisibility(View.GONE);
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView cancel,done;
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    View sView = mLayoutInflator.inflate(R.layout.editing_quantity,null);
                    final EditText editquantity = sView.findViewById(R.id.edit_quantity);
                    cancel = sView.findViewById(R.id.editing_dialog_close);
                    done = sView.findViewById(R.id.editing_dialog_done);

                    alert.setView(sView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (editquantity.getText().toString().isEmpty()){
                                editquantity.setError("Required");
                            }
                            else{
                                final String qq = editquantity.getText().toString();
                                String id = Integer.toString(itemId);

                                Call<EditQuantityModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .editS(qq,id);
                                call.enqueue(new Callback<EditQuantityModel>() {
                                    @Override
                                    public void onResponse(Call<EditQuantityModel> call, Response<EditQuantityModel> response) {
                                        if (response.code() == 201) {
                                            Intent intent = new Intent(mContext, ShoppingListActivity.class);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            Toast.makeText(mContext, "Edited successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, response.message() + " " + response.code() + " found", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<EditQuantityModel> call, Throwable t) {
                                        Toast.makeText(mContext, t.getMessage() + "failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            });

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
                                            .deleteShopping(item_id);
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

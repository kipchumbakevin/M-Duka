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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.DeleteItemModel;
import com.example.shopkipa.models.EditQuantityModel;
import com.example.shopkipa.models.ViewGivenStockModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.GivenStockActivity;
import com.example.shopkipa.ui.ShoppingListActivity;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GivenStockAdapter extends RecyclerView.Adapter<GivenStockAdapter.GivenViewHolders> {
    private final Context mContext;
    private final ArrayList<ViewGivenStockModel> mGivenArrayList;
    private final LayoutInflater mLayoutInflator;

    public GivenStockAdapter(Context context, ArrayList<ViewGivenStockModel>givenStockModels){
        mContext = context;
        mGivenArrayList = givenStockModels;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public GivenViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.givenstockview,parent,false);
        return new GivenViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GivenViewHolders holder, int position) {
        ViewGivenStockModel viewGivenStockModel = mGivenArrayList.get(position);
        holder.headername.setText(viewGivenStockModel.getName());
        holder.color.setText(viewGivenStockModel.getColor());
        holder.itemname.setText(viewGivenStockModel.getName());
        holder.size.setText(viewGivenStockModel.getSize());
        holder.qq = viewGivenStockModel.getGivenQuantity();
        holder.qqq = Integer.toString(holder.qq);
        holder.itemId = viewGivenStockModel.getGivenId();
        holder.quantity.setText(holder.qqq);
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+viewGivenStockModel.getImage())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mGivenArrayList.size();
    }

    public class GivenViewHolders extends RecyclerView.ViewHolder {
        TextView headername,color,itemname,size,quantity;
        ImageView clear,image;
        Button edit;
        int qq,itemId;
        String qqq;
        public GivenViewHolders(@NonNull View itemView) {
            super(itemView);
            headername = itemView.findViewById(R.id.gheader);
            color = itemView.findViewById(R.id.gheader_color);
            itemname = itemView.findViewById(R.id.gitemname);
            size = itemView.findViewById(R.id.gitemsize);
            quantity = itemView.findViewById(R.id.gitemquantity);
            clear = itemView.findViewById(R.id.cleargiven);
            image = itemView.findViewById(R.id.gimage);
            edit = itemView.findViewById(R.id.edit_given);

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
                                        .editG(qq,id);
                                call.enqueue(new Callback<EditQuantityModel>() {
                                    @Override
                                    public void onResponse(Call<EditQuantityModel> call, Response<EditQuantityModel> response) {
                                        if (response.code() == 201) {
                                            Intent intent = new Intent(mContext, GivenStockActivity.class);
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

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to remove from given stock?")
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
                                            .deleteGiven(item_id);
                                    call.enqueue(new Callback<DeleteItemModel>() {
                                        @Override
                                        public void onResponse(Call<DeleteItemModel> call, Response<DeleteItemModel> response) {
                                            if (response.code() == 201) {
                                                Intent intent = new Intent(mContext, GivenStockActivity.class);
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

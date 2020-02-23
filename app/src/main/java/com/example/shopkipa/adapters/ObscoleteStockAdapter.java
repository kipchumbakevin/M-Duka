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
import com.example.shopkipa.models.ViewObscoleteStockModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.ObscoleteStockActivity;
import com.example.shopkipa.ui.ShoppingListActivity;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.header.setText(viewObscoleteStockModel.getName());
        holder.headerColor.setText(viewObscoleteStockModel.getColor());
        holder.itemname.setText(viewObscoleteStockModel.getName());
        holder.size.setText(viewObscoleteStockModel.getSize());
        holder.qq = viewObscoleteStockModel.getObscoleteQuantity();
        holder.qqq = Integer.toString(holder.qq);
        holder.itemId = viewObscoleteStockModel.getObscoleteId();
        holder.quantity.setText(holder.qqq);
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+viewObscoleteStockModel.getImage())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mObscoleteArrayList.size();
    }

    public class ObscoleteViewHolders extends RecyclerView.ViewHolder {
        TextView color,itemname,size,quantity,headerSize,headerColor,header;
        ImageView clear,image,arrowUp,arrowDown;
        Button edit;
        LinearLayoutCompat fulldetails;
        int qq,itemId;
        RelativeLayout dropdown;
        String qqq;
        public ObscoleteViewHolders(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.oitemname);
            size = itemView.findViewById(R.id.oitemsize);
            edit = itemView.findViewById(R.id.edit_obscolete);
            quantity = itemView.findViewById(R.id.oitemquantity);
            clear = itemView.findViewById(R.id.clearobscolete);
            image = itemView.findViewById(R.id.oimage);
            arrowDown = itemView.findViewById(R.id.arrowDown);
            arrowUp = itemView.findViewById(R.id.arrowUp);
            headerSize = itemView.findViewById(R.id.header_size);
            headerColor = itemView.findViewById(R.id.oheader_color);
            header = itemView.findViewById(R.id.oheader);
            fulldetails = itemView.findViewById(R.id.fulldetails);
            dropdown = itemView.findViewById(R.id.dropDown);

            dropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!fulldetails.isShown()) {
                        header.setVisibility(View.GONE);
                        headerColor.setVisibility(View.GONE);
                        headerSize.setVisibility(View.GONE);
                        fulldetails.setVisibility(View.VISIBLE);
                        arrowUp.setVisibility(View.VISIBLE);
                        arrowDown.setVisibility(View.GONE);
                    } else {
                        fulldetails.setVisibility(View.GONE);
                        header.setVisibility(View.VISIBLE);
                        headerSize.setVisibility(View.VISIBLE);
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
                                        .editO(qq,id);
                                call.enqueue(new Callback<EditQuantityModel>() {
                                    @Override
                                    public void onResponse(Call<EditQuantityModel> call, Response<EditQuantityModel> response) {
                                        if (response.code() == 201) {
                                            Intent intent = new Intent(mContext, ObscoleteStockActivity.class);
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
                            .setMessage("Are you sure you want to remove from obscolete stock?")
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
                                            .deleteObsc(item_id);
                                    call.enqueue(new Callback<DeleteItemModel>() {
                                        @Override
                                        public void onResponse(Call<DeleteItemModel> call, Response<DeleteItemModel> response) {
                                            if (response.code() == 201) {
                                                Intent intent = new Intent(mContext, ObscoleteStockActivity.class);
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

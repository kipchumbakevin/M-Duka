package com.example.shopkipa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.example.shopkipa.AddStock;
import com.example.shopkipa.MainActivity;
import com.example.shopkipa.R;
import com.example.shopkipa.models.AddSaleModel;
import com.example.shopkipa.models.DeleteItemModel;
import com.example.shopkipa.models.EditStockModel;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsInTypeAdapter extends RecyclerView.Adapter<ItemsInTypeAdapter.ItemsViewHolder> {

    private final Context mContext;
    private final ArrayList<GetStockInTypeModel> mStockArrayList;
    private final LayoutInflater mLayoutInflator;

    public ItemsInTypeAdapter(Context context, ArrayList<GetStockInTypeModel>stockArrayList){
        mContext = context;
        mStockArrayList = stockArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.items_layout,parent,false);
        Log.d("Fetch", "Reached here too");

        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Log.d("Fetch", "Reached here too");
        GetStockInTypeModel getStockInTypeModel = mStockArrayList.get(position);
        holder.itemname.setText(getStockInTypeModel.getName());
        Glide.with(mContext)
                .load(Constants.BASE_URL+"images/"+getStockInTypeModel.getImage())
                .into(holder.itemImage);
        holder.itemquantity.setText(getStockInTypeModel.getQuantity());
        holder.itemsize.setText(getStockInTypeModel.getSize());
        holder.mCurrentPosition = position;
        holder.itemId = mStockArrayList.get(position).getId();
        holder.purchaseid = mStockArrayList.get(position).getPurchaseId();

    }

    @Override
    public int getItemCount() {
        return mStockArrayList.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemname,itemsize,itemquantity,moreDetails;
        ImageView itemImage,deleteItem;
        Button sold,edit;
        int mCurrentPosition;
        int itemId;
        int purchaseid;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            itemsize = itemView.findViewById(R.id.itemsize);
            itemquantity = itemView.findViewById(R.id.itemquantity);
            itemImage = itemView.findViewById(R.id.itemimage);
            deleteItem = itemView.findViewById(R.id.deleteProduct);
            sold = itemView.findViewById(R.id.soldproduct);
            edit = itemView.findViewById(R.id.editProduct);
            moreDetails = itemView.findViewById(R.id.more_details);

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String item_id = Integer.toString(itemId);

                                    Call<DeleteItemModel> call = RetrofitClient.getInstance(mContext)
                                            .getApiConnector()
                                            .deleteStock(item_id);
                                    call.enqueue(new Callback<DeleteItemModel>() {
                                        @Override
                                        public void onResponse(Call<DeleteItemModel> call, Response<DeleteItemModel> response) {
                                            if(response.code()==201){
                                                Intent intent = new Intent(mContext, MainActivity.class);
                                                mContext.startActivity(intent);
                                                Toast.makeText(mContext,"Deleted successfully",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                               Toast.makeText(mContext,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<DeleteItemModel> call, Throwable t) {
                                            Toast.makeText(mContext,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText editName,editColor,editDesign,editCompany,editSize,editQuantity,editsp;
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View mView = mLayoutInflator.inflate(R.layout.edit_details,null);
                    editName = mView.findViewById(R.id.edit_name);
                    editColor = mView.findViewById(R.id.edit_color);
                    editDesign = mView.findViewById(R.id.edit_design);
                    editCompany = mView.findViewById(R.id.edit_company);
                    editSize = mView.findViewById(R.id.edit_size);
                    editQuantity = mView.findViewById(R.id.edit_quantity);
                    editsp = mView.findViewById(R.id.edit_sp);


                    alertDialogBuilder.setView(mView);
                    alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = editName.getText().toString();
                            String color = editColor.getText().toString();
                            String design = editDesign.getText().toString();
                            String size = editSize.getText().toString();
                            String quantity = editQuantity.getText().toString();
                            String company = editCompany.getText().toString();
                            String sellingprice = editsp.getText().toString();
                            final String item_id = Integer.toString(itemId);


                            Call<EditStockModel> call = RetrofitClient.getInstance(mContext)
                                    .getApiConnector()
                                    .editStock(name,color,design,company,sellingprice,size,quantity,item_id);
                            call.enqueue(new Callback<EditStockModel>() {
                                @Override
                                public void onResponse(Call<EditStockModel> call, Response<EditStockModel> response) {
                                    if(response.code()==201){
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        mContext.startActivity(intent);
                                        ((Activity)mContext).finish();
                                        Toast.makeText(mContext,"Edited successfuly",Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(mContext,"response:"+response.message()+" kkk " + item_id,Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<EditStockModel> call, Throwable t) {
                                    Toast.makeText(mContext,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    GetStockInTypeModel getStockModel = mStockArrayList.get(mCurrentPosition);
                    editName.setText(getStockModel.getName());
                    editColor.setText(getStockModel.getColor());
                    editDesign.setText(getStockModel.getDesign());
                    editCompany.setText(getStockModel.getCompany());
                    editSize.setText(getStockModel.getSize());
                    editQuantity.setText(getStockModel.getQuantity());
                    editsp.setText(getStockModel.getQuantity());
                }
            });

            moreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView itemcolor,itemdesign,itemcompany;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View viewView = mLayoutInflator.inflate(R.layout.more_details,null);
                    itemcolor = viewView.findViewById(R.id.itemcolor);
                    itemdesign = viewView.findViewById(R.id.itemdesign);
                    itemcompany = viewView.findViewById(R.id.itemcompany);

                    alertDialogBuilder.setView(viewView);
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    GetStockInTypeModel getStockModel = mStockArrayList.get(mCurrentPosition);
                    itemcolor.setText(getStockModel.getColor());
                    itemdesign.setText(getStockModel.getDesign());
                    itemcompany.setText(getStockModel.getCompany());

                }
            });


            sold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final GetStockInTypeModel getStockModel = mStockArrayList.get(mCurrentPosition);
                    ImageView dialogsolddone,dialogCancel;
                    final EditText costPrice,quantitySold;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View viewv = mLayoutInflator.inflate(R.layout.soldstock,null);
                    dialogsolddone=viewv.findViewById(R.id.dialog_sold_done);
                    dialogCancel = viewv.findViewById(R.id.dialog_close);
                    costPrice = viewv.findViewById(R.id.cost_unit_price);
                    quantitySold = viewv.findViewById(R.id.quantity_sold);

                    alertDialogBuilder.setView(viewv);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    costPrice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialogBuilder.setMessage("Input the price per item");

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                    });
                    dialogCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    dialogsolddone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (costPrice.getText().toString().isEmpty()){
                                costPrice.setError("Required");
                            }if (quantitySold.getText().toString().isEmpty()){
                                quantitySold.setError("Required");
                            }else{
                                final String quantitysold = quantitySold.getText().toString();
                                String costprice = costPrice.getText().toString();
//                                int id = mStockArrayList.get(mCurrentPosition).getId();
                                final String purchaseId = Integer.toString(purchaseid);
                                Call<AddSaleModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .addnewsale(purchaseId,quantitysold,costprice);
                                call.enqueue(new Callback<AddSaleModel>() {
                                    @Override
                                    public void onResponse(Call<AddSaleModel> call, Response<AddSaleModel> response) {
//                                        progressLyt.setVisibility(View.INVISIBLE);
                                        if(response.code()==201){
                                            Intent intent = new Intent(mContext, MainActivity.class);
                                            mContext.startActivity(intent);
                                            ((Activity)mContext).finish();
                                            Toast.makeText(mContext,"Sale added",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(mContext,response.message()+" "+ response.code() +" found"+purchaseId,Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<AddSaleModel> call, Throwable t) {
                                        Toast.makeText(mContext,t.getMessage()+"failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }
}

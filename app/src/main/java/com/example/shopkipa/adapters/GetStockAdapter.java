package com.example.shopkipa.adapters;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.shopkipa.models.AddSaleModel;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetStockAdapter extends RecyclerView.Adapter<GetStockAdapter.StockViewHolders> {

    private final Context mContext;
    private final ArrayList<GetStockInTypeModel> mStockArrayList;
    private final LayoutInflater mLayoutInflator;
    private int mCurrentPosition;

    public GetStockAdapter(Context context, ArrayList<GetStockInTypeModel>stockArraylist){
        mContext = context;
        mStockArrayList = stockArraylist;
        mLayoutInflator = LayoutInflater.from(mContext);

    }
    @NonNull
    @Override
    public StockViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.stocklayout,parent,false);
        return new StockViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolders holder, int position) {
        GetStockInTypeModel getStockModel = mStockArrayList.get(position);
        holder.itemname.setText(getStockModel.getName());
        holder.itemquantity.setText(getStockModel.getQuantity());
        holder.itemsize.setText(getStockModel.getSize());
        Glide.with(mContext)
                .load(Constants.BASE_URL+"images/"+getStockModel.getImage())
                .into(holder.itemImage);
        mCurrentPosition = position;

    }

    @Override
    public int getItemCount() {
        return mStockArrayList.size();
    }

    public class StockViewHolders extends RecyclerView.ViewHolder {
        TextView itemtype,itemname,itemsize,itemquantity,moreDetails;
        ImageView itemImage,deleteItem,arrowUp,arrowDown;
        RelativeLayout progressLyt,dropDown;
        LinearLayoutCompat fullDetails;
        Button sold,edit;
        public StockViewHolders(@NonNull View itemView) {
            super(itemView);
            itemtype = itemView.findViewById(R.id.itemtype);
            itemname = itemView.findViewById(R.id.itemname);
            itemsize = itemView.findViewById(R.id.itemsize);
            itemquantity = itemView.findViewById(R.id.itemquantity);
            itemImage = itemView.findViewById(R.id.itemimage);
            deleteItem = itemView.findViewById(R.id.deleteProduct);
            progressLyt = itemView.findViewById(R.id.progressLoad);
            dropDown = itemView.findViewById(R.id.dropDown);
            arrowUp = itemView.findViewById(R.id.arrowUp);
            arrowDown = itemView.findViewById(R.id.arrowDown);
            fullDetails = itemView.findViewById(R.id.fulldetails);
            sold = itemView.findViewById(R.id.soldproduct);
            edit = itemView.findViewById(R.id.editProduct);
            moreDetails = itemView.findViewById(R.id.more_details);

            dropDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!fullDetails.isShown()) {
                        fullDetails.setVisibility(View.VISIBLE);
                        arrowDown.setVisibility(View.GONE);
                        arrowUp.setVisibility(View.VISIBLE);
                    }else {
                        fullDetails.setVisibility(View.GONE);
                        arrowDown.setVisibility(View.VISIBLE);
                        arrowUp.setVisibility(View.GONE);
                    }
                }
            });

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext,"Successfully deleted",Toast.LENGTH_SHORT).show();
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
                    EditText editName,editColor,editDesign,editCompany,editSize,editQuantity;
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View mView = mLayoutInflator.inflate(R.layout.edit_details,null);
                    editName = mView.findViewById(R.id.edit_name);
                    editColor = mView.findViewById(R.id.edit_color);
                    editDesign = mView.findViewById(R.id.edit_design);
                    editCompany = mView.findViewById(R.id.edit_company);
                    editSize = mView.findViewById(R.id.edit_size);
                    editQuantity = mView.findViewById(R.id.edit_quantity);


                    alertDialogBuilder.setView(mView);
                    alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(mContext,"Change succesfull",Toast.LENGTH_SHORT).show();
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
                    editName.setHint(getStockModel.getName());
                    editColor.setHint(getStockModel.getColor());
                    editDesign.setHint(getStockModel.getDesign());
                    editCompany.setHint(getStockModel.getCompany());
                    editSize.setHint(getStockModel.getSize());
                    editQuantity.setHint(getStockModel.getQuantity());
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
//                                progressLyt.setVisibility(View.VISIBLE);
                                final String quantitysold = quantitySold.getText().toString();
                                String costprice = costPrice.getText().toString();
                                int id = mStockArrayList.get(mCurrentPosition).getId();
                                String purchaseId = Integer.toString(id);
                                Call<AddSaleModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .addnewsale(purchaseId,quantitysold,costprice);
                                call.enqueue(new Callback<AddSaleModel>() {
                                    @Override
                                    public void onResponse(Call<AddSaleModel> call, Response<AddSaleModel> response) {
//                                        progressLyt.setVisibility(View.INVISIBLE);
                                        if(response.code()==201){
                                            Toast.makeText(mContext,response.message()+"done",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(mContext,response.message()+"found",Toast.LENGTH_SHORT).show();
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

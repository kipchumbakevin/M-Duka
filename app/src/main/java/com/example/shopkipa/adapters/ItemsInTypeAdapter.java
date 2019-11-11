package com.example.shopkipa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.shopkipa.models.GetBuyingPricesModel;
import com.example.shopkipa.models.GetSizeModel;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.RestockModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;
import java.util.List;

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
                .load(getStockInTypeModel.getImage())
                .into(holder.itemImage);
        holder.itemquantity.setText(getStockInTypeModel.getQuantity());
        holder.itemsize.setText(getStockInTypeModel.getSize());
        holder.mCurrentPosition = position;
        holder.itemId = mStockArrayList.get(position).getId();
        holder.purchaseid = mStockArrayList.get(position).getPurchaseId();
        holder.header.setText(getStockInTypeModel.getName());
        holder.headerSize.setText("("+getStockInTypeModel.getSize()+")");
        holder.headerColor.setText("("+getStockInTypeModel.getColor()+")");
        holder.q = getStockInTypeModel.getQuantity();
    }

    @Override
    public int getItemCount() {
        return mStockArrayList.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, itemsize, itemquantity, moreDetails, header, headerSize, headerColor;
        ImageView itemImage, deleteItem, restock, arrowUp, arrowDown, cancelSale, addSale,alert;
        Button sold, edit;
        int mCurrentPosition;
        LinearLayoutCompat fulldetails, linearSale;
        RelativeLayout dropdown, relativeSale;
        EditText quantitySold, costUnitPrice;
        int itemId;
        int purchaseid;
        String q;
        private ArrayAdapter<String>bpadapter;
        private List<String> bpSpinnerArray;
        Spinner bpSpinner;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            itemsize = itemView.findViewById(R.id.itemsize);
            itemquantity = itemView.findViewById(R.id.itemquantity);
            itemImage = itemView.findViewById(R.id.itemimage);
            dropdown = itemView.findViewById(R.id.dropDown);
            headerColor = itemView.findViewById(R.id.header_color);
            linearSale = itemView.findViewById(R.id.linearSale);
            bpSpinner = itemView.findViewById(R.id.bpspinner);
            costUnitPrice = itemView.findViewById(R.id.cost_unit_price);
            relativeSale = itemView.findViewById(R.id.relativeSale);
            cancelSale = itemView.findViewById(R.id.cancel_sale);
            addSale = itemView.findViewById(R.id.sold_done);
            headerSize = itemView.findViewById(R.id.header_size);
            fulldetails = itemView.findViewById(R.id.fulldetails);
            alert = itemView.findViewById(R.id.alert);
            arrowDown = itemView.findViewById(R.id.arrowDown);
            arrowUp = itemView.findViewById(R.id.arrowUp);
            header = itemView.findViewById(R.id.header);
            restock = itemView.findViewById(R.id.restock);
            deleteItem = itemView.findViewById(R.id.deleteProduct);
            sold = itemView.findViewById(R.id.soldproduct);
            edit = itemView.findViewById(R.id.editProduct);
            moreDetails = itemView.findViewById(R.id.more_details);

            bpSpinnerArray = new ArrayList<>();

            bpadapter = new ArrayAdapter<>(
                    mContext, android.R.layout.simple_spinner_item, bpSpinnerArray);

            bpadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            bpSpinner.setAdapter(bpadapter);
            String item_id = Integer.toString(itemId);
            Call<List<GetBuyingPricesModel>> call = RetrofitClient.getInstance(mContext)
                    .getApiConnector()
                    .getBP(item_id);
            call.enqueue(new Callback<List<GetBuyingPricesModel>>() {
                @Override
                public void onResponse(Call<List<GetBuyingPricesModel>> call, Response<List<GetBuyingPricesModel>> response) {
                    Log.d("cc", response.code()+"");
                    if(response.isSuccessful()){

                        for(int index= 0;index<response.body().size();index++){
                            bpSpinnerArray.add(response.body().get(index).getAmount());
                        }
                        bpadapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(mContext,"response: " +response.message()+response.code(),Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<GetBuyingPricesModel>> call, Throwable t) {
                    Toast.makeText(mContext,"Error: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

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
            restock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText quantityBought, buyingPrice;
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    View viewView = mLayoutInflator.inflate(R.layout.restock, null);
                    quantityBought = viewView.findViewById(R.id.quantity_bought);
                    buyingPrice = viewView.findViewById(R.id.cost_per_unit);

                    alert.setView(viewView)
                            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (quantityBought.getText().toString().isEmpty()) {
                                        quantityBought.setError("Required");
                                    }
                                    if (buyingPrice.getText().toString().isEmpty()) {
                                        buyingPrice.setError("Required");
                                    } else {
                                        String quantity = quantityBought.getText().toString();
                                        String buyingprice = buyingPrice.getText().toString();
                                        final String item_id = Integer.toString(itemId);
                                        Call<RestockModel> call = RetrofitClient.getInstance(mContext)
                                                .getApiConnector()
                                                .restock(quantity, buyingprice, item_id);
                                        call.enqueue(new Callback<RestockModel>() {
                                            @Override
                                            public void onResponse(Call<RestockModel> call, Response<RestockModel> response) {
                                                if (response.code() == 201) {
                                                    Intent intent = new Intent(mContext, MainActivity.class);
                                                    mContext.startActivity(intent);
                                                    ((Activity) mContext).finish();
                                                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(mContext, "response:" + response.message() + " kkk " + item_id, Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<RestockModel> call, Throwable t) {
                                                Toast.makeText(mContext, "errot:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
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
                                    String item_id = Integer.toString(itemId);

                                    Call<DeleteItemModel> call = RetrofitClient.getInstance(mContext)
                                            .getApiConnector()
                                            .deleteStock(item_id);
                                    call.enqueue(new Callback<DeleteItemModel>() {
                                        @Override
                                        public void onResponse(Call<DeleteItemModel> call, Response<DeleteItemModel> response) {
                                            if (response.code() == 201) {
                                                Intent intent = new Intent(mContext, MainActivity.class);
                                                mContext.startActivity(intent);
                                                Toast.makeText(mContext, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mContext, "response:" + response.message(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<DeleteItemModel> call, Throwable t) {
                                            Toast.makeText(mContext, "errot:" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    final EditText editName, editsp;
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View mView = mLayoutInflator.inflate(R.layout.edit_details, null);
                    editName = mView.findViewById(R.id.edit_name);
                    editsp = mView.findViewById(R.id.edit_sp);

                    alertDialogBuilder.setView(mView);
                    alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = editName.getText().toString();
                            String sellingprice = editsp.getText().toString();
                            final String item_id = Integer.toString(itemId);


                            Call<EditStockModel> call = RetrofitClient.getInstance(mContext)
                                    .getApiConnector()
                                    .editStock(name, sellingprice, item_id);
                            call.enqueue(new Callback<EditStockModel>() {
                                @Override
                                public void onResponse(Call<EditStockModel> call, Response<EditStockModel> response) {
                                    if (response.code() == 201) {
                                        Toast.makeText(mContext, "Edited successfuly", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        mContext.startActivity(intent);
                                        ((Activity) mContext).finish();

                                    } else {
                                        Toast.makeText(mContext, "response:" + response.message() + " kkk " + item_id, Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<EditStockModel> call, Throwable t) {
                                    Toast.makeText(mContext, "errot:" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    editsp.setText(getStockModel.getQuantity());
                }
            });

            moreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView itemcolor, itemdesign, itemcompany;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View viewView = mLayoutInflator.inflate(R.layout.more_details, null);
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
                    linearSale.setVisibility(View.GONE);
                    relativeSale.setVisibility(View.VISIBLE);
                }
            });
            cancelSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearSale.setVisibility(View.VISIBLE);
                    relativeSale.setVisibility(View.GONE);
                }
            });
            addSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (costUnitPrice.getText().toString().isEmpty()) {
                        costUnitPrice.setError("Required");
                    }
                    if (quantitySold.getText().toString().isEmpty()) {
                        quantitySold.setError("Required");
                    } else {
                        final String quantitysold = quantitySold.getText().toString();
                        String costprice = costUnitPrice.getText().toString();
                        final String purchaseId = Integer.toString(purchaseid);
                        Call<AddSaleModel> call = RetrofitClient.getInstance(mContext)
                                .getApiConnector()
                                .addnewsale(purchaseId, quantitysold, costprice);
                        call.enqueue(new Callback<AddSaleModel>() {
                            @Override
                            public void onResponse(Call<AddSaleModel> call, Response<AddSaleModel> response) {
//                                        progressLyt.setVisibility(View.INVISIBLE);
                                if (response.code() == 201) {
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    mContext.startActivity(intent);
                                    ((Activity) mContext).finish();
                                    Toast.makeText(mContext, "Sale added", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, response.message() + " " + response.code() + " found" + purchaseId, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<AddSaleModel> call, Throwable t) {
                                Toast.makeText(mContext, t.getMessage() + "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }
}

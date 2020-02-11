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
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.shopkipa.models.AddGivenStockModel;
import com.example.shopkipa.models.AddObscoleteModel;
import com.example.shopkipa.models.AddShoppingListModel;
import com.example.shopkipa.ui.MainActivity;
import com.example.shopkipa.R;
import com.example.shopkipa.models.AddSaleModel;
import com.example.shopkipa.models.DeleteItemModel;
import com.example.shopkipa.models.EditStockModel;
import com.example.shopkipa.models.GetBuyingPricesModel;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.RestockModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.ViewPhotos;
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

        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        GetStockInTypeModel getStockInTypeModel = mStockArrayList.get(position);
        holder.itemname.setText(getStockInTypeModel.getName());
        Glide.with(mContext).load(Constants.BASE_URL + "images/"+getStockInTypeModel.getImage())
                .into(holder.itemImage);
//        ViewPagerAdapter.images = new String[]{Constants.BASE_URL + "images/"+getStockInTypeModel.getImage()};
//        holder.viewPager.setAdapter(holder.viewPagerAdapter);
        holder.itemsize.setText(getStockInTypeModel.getSize());
        holder.mCurrentPosition = position;
        holder.itemId = mStockArrayList.get(position).getId();
        holder.purchaseid = mStockArrayList.get(position).getPurchaseId();
        holder.header.setText(getStockInTypeModel.getName());
        holder.headerSize.setText("("+getStockInTypeModel.getSize()+")");
        holder.headerColor.setText("("+getStockInTypeModel.getColor()+")");
        holder.idItem = Integer.toString(mStockArrayList.get(position).getId());
        holder.itemQua = mStockArrayList.get(position).getQuantity();
        holder.qq = Integer.toString(holder.itemQua);
        holder.itemquantity.setText(holder.qq);

    }

    @Override
    public int getItemCount() {
        return mStockArrayList.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, itemsize, itemquantity, moreDetails, header, headerSize, headerColor;
        ImageView deleteItem, restock, arrowUp, arrowDown,itemImage,shoppingList;
        Button sold, edit,given,obscolete;
        int mCurrentPosition;
        String idItem;
        int itemQua;
        String qq;
        LinearLayoutCompat fulldetails, linearSale;
        RelativeLayout dropdown,noProducts,progressL;
        EditText quantitySold, costUnitPrice;
        ViewPager viewPager;
        int itemId;
        int purchaseid;
        private ArrayAdapter<String>bpadapter;
        private List<String> bpSpinnerArray;
        private ViewPagerAdapter viewPagerAdapter;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            itemsize = itemView.findViewById(R.id.itemsize);
            itemquantity = itemView.findViewById(R.id.itemquantity);
            itemImage = itemView.findViewById(R.id.itemimagees);
            shoppingList = itemView.findViewById(R.id.addToShoppingList);
            dropdown = itemView.findViewById(R.id.dropDown);
//            viewPagerAdapter = new ViewPagerAdapter(mContext);
//            viewPager = itemView.findViewById(R.id.viewPagerAdapterIm);
            headerColor = itemView.findViewById(R.id.header_color);
            linearSale = itemView.findViewById(R.id.linearSale);
            costUnitPrice = itemView.findViewById(R.id.cost_unit_price);
            noProducts = itemView.findViewById(R.id.no_products_view);
            headerSize = itemView.findViewById(R.id.header_size);
            fulldetails = itemView.findViewById(R.id.fulldetails);
            quantitySold = itemView.findViewById(R.id.quantity_sold);
            arrowDown = itemView.findViewById(R.id.arrowDown);
            arrowUp = itemView.findViewById(R.id.arrowUp);
            header = itemView.findViewById(R.id.header);
            given = itemView.findViewById(R.id.given);
            obscolete = itemView.findViewById(R.id.obscolete);
            restock = itemView.findViewById(R.id.restock);
            deleteItem = itemView.findViewById(R.id.deleteProduct);
            sold = itemView.findViewById(R.id.soldproduct);
            edit = itemView.findViewById(R.id.editProduct);
            moreDetails = itemView.findViewById(R.id.more_details);

            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = Integer.toString(itemId);
                    Intent intent = new Intent(mContext, ViewPhotos.class);
                    intent.putExtra("ITEMID",id);
                    mContext.startActivity(intent);
                }
            });
            obscolete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView obscCancel,obscDone;
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    View sView = mLayoutInflator.inflate(R.layout.obscoletestock,null);
                    final EditText oquantity = sView.findViewById(R.id.shoppingAmount);
                    obscCancel = sView.findViewById(R.id.shopping_dialog_close);
                    obscDone = sView.findViewById(R.id.shopping_dialog_done);
                    progressL = sView.findViewById(R.id.progressLoad);

                    alert.setView(sView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    obscCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    obscDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(oquantity.getText().toString().isEmpty()){
                                oquantity.setError("Required");
                            }
                            else{
                                showProgress();
                                final String obscQ = oquantity.getText().toString();
                                String itemid = Integer.toString(itemId);

                                Call<AddObscoleteModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .addobsc(itemid,obscQ);
                                call.enqueue(new Callback<AddObscoleteModel>() {
                                    @Override
                                    public void onResponse(Call<AddObscoleteModel> call, Response<AddObscoleteModel> response) {
                                        hideProgress();
                                        if (response.code() == 201) {
                                            Intent intent = new Intent(mContext, MainActivity.class);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            Toast.makeText(mContext, "Added to obscolete stock", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, response.message() + " " + response.code() + " found", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<AddObscoleteModel> call, Throwable t) {
                                        hideProgress();
                                        Toast.makeText(mContext, t.getMessage() + "failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            });
            given.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView givenCancel,givenDone;
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    View sView = mLayoutInflator.inflate(R.layout.givenstock,null);
                    final EditText gquantity = sView.findViewById(R.id.shoppingAmount);
                    givenCancel = sView.findViewById(R.id.shopping_dialog_close);
                    givenDone = sView.findViewById(R.id.shopping_dialog_done);
                    progressL = sView.findViewById(R.id.progressLoad);

                    alert.setView(sView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    givenCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    givenDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(gquantity.getText().toString().isEmpty()){
                                gquantity.setError("Required");
                            }
                            else{
                                showProgress();
                                final String givenQ = gquantity.getText().toString();
                                String itemid = Integer.toString(itemId);

                                Call<AddGivenStockModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .addgiven(itemid,givenQ);
                                call.enqueue(new Callback<AddGivenStockModel>() {
                                    @Override
                                    public void onResponse(Call<AddGivenStockModel> call, Response<AddGivenStockModel> response) {
                                        hideProgress();
                                        if (response.code() == 201) {
                                            Intent intent = new Intent(mContext, MainActivity.class);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            Toast.makeText(mContext, "Added to given stock", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, response.message() + " " + response.code() + " found", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<AddGivenStockModel> call, Throwable t) {
                                        hideProgress();
                                        Toast.makeText(mContext, t.getMessage() + "failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            });

            shoppingList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView shoppingCancel,shoppingDone;
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    View sView = mLayoutInflator.inflate(R.layout.addtoshopping,null);
                    final EditText squantity = sView.findViewById(R.id.shoppingAmount);
                    shoppingCancel = sView.findViewById(R.id.shopping_dialog_close);
                    shoppingDone = sView.findViewById(R.id.shopping_dialog_done);
                    progressL = sView.findViewById(R.id.progressLoad);

                    alert.setView(sView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    shoppingCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    shoppingDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(squantity.getText().toString().isEmpty()){
                                squantity.setError("Required");
                            }
                            else{
                                showProgress();
                                final String shopQ = squantity.getText().toString();
                                String itemid = Integer.toString(itemId);

                                Call<AddShoppingListModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .addshoppinglist(itemid,shopQ);
                                call.enqueue(new Callback<AddShoppingListModel>() {
                                    @Override
                                    public void onResponse(Call<AddShoppingListModel> call, Response<AddShoppingListModel> response) {
                                        hideProgress();
                                        if (response.code() == 201) {
                                            Intent intent = new Intent(mContext, MainActivity.class);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            Toast.makeText(mContext, "Added to shopping list", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, response.message() + " " + response.code() + " found", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<AddShoppingListModel> call, Throwable t) {
                                        hideProgress();
                                        Toast.makeText(mContext, t.getMessage() + "failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
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
                    ImageView cancel,done;
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    View viewView = mLayoutInflator.inflate(R.layout.restock, null);
                    quantityBought = viewView.findViewById(R.id.quantity_bought);
                    buyingPrice = viewView.findViewById(R.id.cost_per_unit);
                    done = viewView.findViewById(R.id.dialog_done_adds);
                    cancel = viewView.findViewById(R.id.dialog_close_adds);
                    progressL = viewView.findViewById(R.id.progressLoad);

                    alert.setView(viewView);
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
                            if (quantityBought.getText().toString().isEmpty()) {
                                quantityBought.setError("Required");
                            }
                            if (buyingPrice.getText().toString().isEmpty()) {
                                buyingPrice.setError("Required");
                            } else {
                                String quantity = quantityBought.getText().toString();
                                String buyingprice = buyingPrice.getText().toString();
                                final String item_id = Integer.toString(itemId);
                                showProgress();
                                Call<RestockModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .restock(quantity, buyingprice, item_id);
                                call.enqueue(new Callback<RestockModel>() {
                                    @Override
                                    public void onResponse(Call<RestockModel> call, Response<RestockModel> response) {
                                        hideProgress();
                                        if (response.code() == 201) {
                                            Intent intent = new Intent(mContext, MainActivity.class);
                                            mContext.startActivity(intent);
                                            ((Activity) mContext).finish();
                                            Toast.makeText(mContext, "Purchase added successfully", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(mContext, "response:" + response.message() + " kkk " + item_id, Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<RestockModel> call, Throwable t) {
                                        hideProgress();
                                        Toast.makeText(mContext, "errot:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
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
                                    final String item_id = Integer.toString(itemId);

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
                                                Toast.makeText(mContext, "response:" + " "+ item_id+" "+response.message(), Toast.LENGTH_SHORT).show();
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
                    progressL = mView.findViewById(R.id.progressLoad);
                    editsp = mView.findViewById(R.id.edit_sp);

                    alertDialogBuilder.setView(mView);
                    alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showProgress();
                            String name = editName.getText().toString();
                            String sellingprice = editsp.getText().toString();
                            final String item_id = Integer.toString(itemId);


                            Call<EditStockModel> call = RetrofitClient.getInstance(mContext)
                                    .getApiConnector()
                                    .editStock(name, sellingprice, item_id);
                            call.enqueue(new Callback<EditStockModel>() {
                                @Override
                                public void onResponse(Call<EditStockModel> call, Response<EditStockModel> response) {
                                    hideProgress();
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
                                    hideProgress();
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
                    editsp.setText(getStockModel.getSellingprice());
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
                    final EditText quantity,perQuantity;
                    final Spinner bpSpinner;
                    ImageView cancel,done;
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    View view1 = mLayoutInflator.inflate(R.layout.soldstock,null);
                    quantity = view1.findViewById(R.id.quantity_sold);
                    perQuantity = view1.findViewById(R.id.cost_unit_price);
                    bpSpinner = view1.findViewById(R.id.bpspinner);
                    progressL = view1.findViewById(R.id.progressLoad);
                    cancel = view1.findViewById(R.id.dialog_close);
                    done = view1.findViewById(R.id.dialog_sold_done);
                    bpSpinnerArray = new ArrayList<>();

                    bpadapter = new ArrayAdapter<>(
                            mContext, android.R.layout.simple_spinner_item, bpSpinnerArray);

                    bpadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    bpSpinner.setAdapter(bpadapter);
                    String i = Integer.toString(itemId);
                    showProgress();
                    bpSpinnerArray.clear();
                    Call<List<GetBuyingPricesModel>> call = RetrofitClient.getInstance(mContext)
                            .getApiConnector()
                            .getBP(i);
                    call.enqueue(new Callback<List<GetBuyingPricesModel>>() {
                        @Override
                        public void onResponse(Call<List<GetBuyingPricesModel>> call, Response<List<GetBuyingPricesModel>> response) {
                            hideProgress();
                            if(response.code()==200){

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
                            hideProgress();
                            Toast.makeText(mContext,"Error: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setView(view1);
                    final AlertDialog alertDialog1 = alertDialog.create();
                    alertDialog1.show();
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog1.dismiss();
                        }
                    });
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int iii=0;
                            if (!quantity.getText().toString().isEmpty()){
                                iii = Integer.parseInt(quantity.getText().toString());
                            }
                            if (quantity.getText().toString().isEmpty()) {
                                quantity.setError("Required");
                            }
                            if (perQuantity.getText().toString().isEmpty()) {
                                perQuantity.setError("Required");
                            }

                            if(iii>itemQua){
                                quantity.setError("You only have "+itemQua);
                                Toast.makeText(mContext,"You only have "+itemQua +  " items of this product",Toast.LENGTH_LONG).show();
                            }
                            else {
                                showProgress();
                                final String quantitysold = quantity.getText().toString();
                                String costprice = perQuantity.getText().toString();
                                final String purchaseId = Integer.toString(purchaseid);
                                String bp = bpSpinner.getSelectedItem().toString();
                                String itemid = Integer.toString(itemId);

                                Call<AddSaleModel> call = RetrofitClient.getInstance(mContext)
                                        .getApiConnector()
                                        .addnewsale(purchaseId, quantitysold, costprice,bp,itemid);
                                call.enqueue(new Callback<AddSaleModel>() {
                                    @Override
                                    public void onResponse(Call<AddSaleModel> call, Response<AddSaleModel> response) {
                                        hideProgress();
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
                                        hideProgress();
                                        Toast.makeText(mContext, t.getMessage() + "failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        private void showProgress() {
            progressL.setVisibility(View.VISIBLE);
        }
        private void hideProgress() {
            progressL.setVisibility(View.GONE);
        }
    }
}

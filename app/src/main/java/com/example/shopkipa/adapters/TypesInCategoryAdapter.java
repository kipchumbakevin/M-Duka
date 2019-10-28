package com.example.shopkipa.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.GetTypesInCategoryModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypesInCategoryAdapter extends RecyclerView.Adapter<TypesInCategoryAdapter.TypesViewHolder> {

    private final Context mContext;
    private final ArrayList<GetTypesInCategoryModel> mTypesArrayList;
    private final ArrayList<GetStockInTypeModel> mStockArrayList = new ArrayList<>();
   private ItemsInTypeAdapter itemsInTypeAdapter;
    private final LayoutInflater mLayoutInflator;

    public TypesInCategoryAdapter(Context context, ArrayList<GetTypesInCategoryModel>typesArrayList){
        mContext = context;
        mTypesArrayList = typesArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);

    }
    @NonNull
    @Override
    public TypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.stocklayout,parent,false);
        return new TypesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypesViewHolder holder, int position) {


        GetTypesInCategoryModel getTypesInCategoryModel = mTypesArrayList.get(position);
        holder.itemType.setText(getTypesInCategoryModel.getTypeName());

       holder.currentPosition=position;


    }

    @Override
    public int getItemCount() {
        return mTypesArrayList.size();
    }

    public class TypesViewHolder extends RecyclerView.ViewHolder {
        TextView itemType;
        RelativeLayout dropdown;
        LinearLayoutCompat full;
        RecyclerView itemsrecycler;
        int currentPosition;
        public TypesViewHolder(@NonNull View itemView) {
            super(itemView);
            itemsInTypeAdapter = new ItemsInTypeAdapter(mContext,mStockArrayList);
            itemType = itemView.findViewById(R.id.itemtype);
            dropdown = itemView.findViewById(R.id.dropDown);
            itemsrecycler = itemView.findViewById(R.id.items_recyclerView);
            full = itemView.findViewById(R.id.full);

            dropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!full.isShown()){
                        full.setVisibility(View.VISIBLE);
                        itemsrecycler.setLayoutManager(new GridLayoutManager(mContext,1));
                        itemsrecycler.setAdapter(itemsInTypeAdapter);
                    }else{
                        full.setVisibility(View.GONE);
                    }
                }
            });
//itemView.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        itemsrecycler.setLayoutManager(new GridLayoutManager(mContext,1));
//        itemsrecycler.setAdapter(itemsInTypeAdapter);
//        viewStock(mTypesArrayList.get(currentPosition).getId().toString());
//    }
//});

        }
    }




}

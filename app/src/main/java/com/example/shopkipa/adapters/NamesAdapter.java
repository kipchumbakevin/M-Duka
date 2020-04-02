package com.example.shopkipa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetNamesModel;

import java.util.ArrayList;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.NamesViewHolder> {
    private final Context mContext;
    private final ArrayList<GetNamesModel> mNamesArrayList;
    private final LayoutInflater mLayoutInflator;

    public NamesAdapter(Context context, ArrayList<GetNamesModel>namesArray){
        this.mContext = context;
        this.mNamesArrayList = namesArray;
        this.mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public NamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.names_layout,parent,false);
        return new NamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NamesViewHolder holder, int position) {
        GetNamesModel getNamesModel = mNamesArrayList.get(position);
        holder.name.setText(getNamesModel.getName());
    }

    @Override
    public int getItemCount() {
        return mNamesArrayList.size();
    }

    public class NamesViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public NamesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.get_names);
        }
    }
}

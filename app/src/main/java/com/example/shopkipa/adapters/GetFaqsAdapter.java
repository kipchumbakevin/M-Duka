package com.example.shopkipa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetFaqsModel;

import java.util.ArrayList;

public class GetFaqsAdapter extends RecyclerView.Adapter<GetFaqsAdapter.GetFaqsViewHolder> {

    private final Context mContext;
    private final ArrayList<GetFaqsModel> mFaqsArrayList;
    private final LayoutInflater mLayoutInflator;

    public GetFaqsAdapter(Context context, ArrayList<GetFaqsModel>faqsArrayList){
        mContext = context;
        mFaqsArrayList = faqsArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public GetFaqsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.faqs_view,parent,false);
        return new GetFaqsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetFaqsViewHolder holder, int position) {
        GetFaqsModel getFaqsModel = mFaqsArrayList.get(position);
        holder.faq_question.setText(getFaqsModel.getQuestion());
        holder.faq_answer.setText(getFaqsModel.getAnswer());

    }

    @Override
    public int getItemCount() {
        return mFaqsArrayList.size();
    }

    public class GetFaqsViewHolder extends RecyclerView.ViewHolder {
        TextView faq_question,faq_answer;
        public GetFaqsViewHolder(@NonNull View itemView) {
            super(itemView);
            faq_question = itemView.findViewById(R.id.faq_question);
            faq_answer = itemView.findViewById(R.id.faq_answer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!faq_answer.isShown()){
                        faq_answer.setVisibility(View.VISIBLE);
                    }else{
                        faq_answer.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}

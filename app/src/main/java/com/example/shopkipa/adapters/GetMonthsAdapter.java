package com.example.shopkipa.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.models.GetMonthsModel;
import com.example.shopkipa.models.GetSalesInMonthModel;
import com.example.shopkipa.models.GetSummaryModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMonthsAdapter extends RecyclerView.Adapter<GetMonthsAdapter.GetMonthsViewHolder> {

    private static final String DIALOG_EXPENSES = "My Expenses";
    private final Context mContext;
    private final ArrayList<GetMonthsModel> mMonthsArrayList;
    private final LayoutInflater mLayoutInflator;

    public GetMonthsAdapter(Context context, ArrayList<GetMonthsModel>monthArrayList){
        mContext = context;
        mMonthsArrayList = monthArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public GetMonthsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.view_months,parent,false);
        return new GetMonthsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetMonthsViewHolder holder, int position) {
        GetMonthsModel getMonthsModel = mMonthsArrayList.get(position);
        holder.mMonth.setText(getMonthsModel.getMonth());
        holder.year = mMonthsArrayList.get(position).getYear();
        holder.month = mMonthsArrayList.get(position).getMonth();
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mMonthsArrayList.size();
    }

    public class GetMonthsViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat fullDetails;
        RelativeLayout dropDown;
        ImageView arrowUp,arrowDown;
        int mCurrentPosition;
        String year,month;
        TextView mMonth,summaryMonth,salesMonth,expensesMonth;
        public GetMonthsViewHolder(@NonNull View itemView) {
            super(itemView);
            mMonth = itemView.findViewById(R.id.month);
            expensesMonth = itemView.findViewById(R.id.monthExpenses);
            dropDown = itemView.findViewById(R.id.dropDown);
            arrowUp = itemView.findViewById(R.id.arrowUp);
            arrowDown = itemView.findViewById(R.id.arrowDown);
            salesMonth = itemView.findViewById(R.id.monthSales);
            fullDetails = itemView.findViewById(R.id.full);
            summaryMonth = itemView.findViewById(R.id.monthSummary);
            GetMonthsModel get = mMonthsArrayList.get(mCurrentPosition);
            final String nn = get.getMonth();

            expensesMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View mView = mLayoutInflator.inflate(R.layout.all_expenses,null);
                    TextView title = mView.findViewById(R.id.titleMonthExpenses);
                    RecyclerView recyclerView = mView.findViewById(R.id.expenses_recyclerView);
                    final ArrayList<GetExpenseModel> mExpensesArrayList = new ArrayList<>();
                    final ViewExpensesAdapter viewExpensesAdapter = new ViewExpensesAdapter(mContext,mExpensesArrayList);
                    recyclerView.setAdapter(viewExpensesAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext,1));
                    alertDialogBuilder.setView(mView);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ArrayList<GetExpenseModel> mExpensesArray;
                    mExpensesArrayList.clear();
                    Call<List<GetExpenseModel>> call = RetrofitClient.getInstance(mContext)
                            .getApiConnector()
                            .getAllExpenses(year,month);
                    call.enqueue(new Callback<List<GetExpenseModel>>() {
                        @Override
                        public void onResponse(Call<List<GetExpenseModel>> call, Response<List<GetExpenseModel>> response) {
                            if(response.code()==200){
                                mExpensesArrayList.addAll(response.body());
                                viewExpensesAdapter.notifyDataSetChanged();

                            }
                            else{
                                Toast.makeText(mContext,response.message() + response.code() + "rr",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<GetExpenseModel>> call, Throwable t) {
                            Toast.makeText(mContext,t.getMessage() + "failed",Toast.LENGTH_LONG).show();
                        }

                    });
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    title.setText(nn + " expenses");
                }
            });
            salesMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View mViewv = mLayoutInflator.inflate(R.layout.all_sales,null);
                    TextView title = mViewv.findViewById(R.id.titleMonthSales);
                    RecyclerView recyclerView = mViewv.findViewById(R.id.sales_recyclerView);
                    final ArrayList<GetSalesInMonthModel> mSalesArrayList = new ArrayList<>();
                    final ViewSalesAdapter viewSalesAdapter = new ViewSalesAdapter(mContext,mSalesArrayList);
                    recyclerView.setAdapter(viewSalesAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext,1));
                    alertDialogBuilder.setView(mViewv);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ArrayList<GetSalesInMonthModel> mSalesArray;
                    mSalesArrayList.clear();
                    Call<List<GetSalesInMonthModel>> call = RetrofitClient.getInstance(mContext)
                            .getApiConnector()
                            .getAllSales(year,month);
                    call.enqueue(new Callback<List<GetSalesInMonthModel>>() {
                        @Override
                        public void onResponse(Call<List<GetSalesInMonthModel>> call, Response<List<GetSalesInMonthModel>> response) {
                            if(response.code()==200){
                                mSalesArrayList.addAll(response.body());
                                viewSalesAdapter.notifyDataSetChanged();

                            }
                            else{
                                Toast.makeText(mContext,response.message() + response.code() + "rr",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<GetSalesInMonthModel>> call, Throwable t) {
                            Toast.makeText(mContext,t.getMessage() + "failed",Toast.LENGTH_LONG).show();
                        }

                    });
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    title.setText(nn + " sales");
                }
            });
            summaryMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    View mViewV = mLayoutInflator.inflate(R.layout.summary_layout,null);
                    TextView title = mViewV.findViewById(R.id.titleMonthSummary);
                    RecyclerView recyclerView = mViewV.findViewById(R.id.summary_recyclerView);
                    final ArrayList<GetSummaryModel> mSummaryArrayList = new ArrayList<>();
                    final GetSummaryAdapter getSummaryAdapter = new GetSummaryAdapter(mContext,mSummaryArrayList);
                    recyclerView.setAdapter(getSummaryAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext,1));

                    alertDialogBuilder.setView(mViewV);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ArrayList<GetSummaryModel> mSalesArray;
                    mSummaryArrayList.clear();
                    Call<GetSummaryModel> call = RetrofitClient.getInstance(mContext)
                            .getApiConnector()
                            .getSummary(year,month);
                    call.enqueue(new Callback<GetSummaryModel>() {
                        @Override
                        public void onResponse(Call<GetSummaryModel> call, Response<GetSummaryModel> response) {
                            if(response.code()==200){
                                Log.i("mm ", ""+response.body().getTotalProfit());
                                mSummaryArrayList.add(response.body());
                                getSummaryAdapter.notifyDataSetChanged();

                            }
                            else{
                                Toast.makeText(mContext,response.message() + response.code() + "rr",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetSummaryModel> call, Throwable t) {
                            Toast.makeText(mContext,t.getMessage() + "failed",Toast.LENGTH_LONG).show();
                        }

                    });
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    title.setText(nn + " summary");
                }
            });
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
        }
    }
}

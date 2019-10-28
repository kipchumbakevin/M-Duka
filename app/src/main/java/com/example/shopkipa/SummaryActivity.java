package com.example.shopkipa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.shopkipa.adapters.GetSalesAdapter;
import com.example.shopkipa.models.GetSalesModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GetSalesAdapter getSalesAdapter;
    ArrayList<GetSalesModel>mSalesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        recyclerView = findViewById(R.id.sales_recyclerView);
        getSalesAdapter = new GetSalesAdapter(SummaryActivity.this,mSalesArrayList);
        recyclerView.setAdapter(getSalesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(SummaryActivity.this,1));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        viewSales();
    }

    private void viewSales() {
        ArrayList<GetSalesModel> mSalesArray;
        mSalesArrayList.clear();
        Call<GetSalesModel> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .getsale();
        call.enqueue(new Callback<GetSalesModel>() {
            @Override
            public void onResponse(Call<GetSalesModel> call, Response<GetSalesModel> response) {
                if(response.code()==200){
                    mSalesArrayList.add(response.body());
                    getSalesAdapter.notifyDataSetChanged();

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<GetSalesModel> call, Throwable t) {
                Toast.makeText(SummaryActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });
    }
}

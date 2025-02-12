package com.example.shopkipa.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.GetMonthsAdapter;
import com.example.shopkipa.models.GetMonthsModel;
import com.example.shopkipa.models.GetYearsModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Spinner yearSpinner;
    GetMonthsAdapter getMonthsAdapter;
    ArrayList<GetMonthsModel>mMonthsArrayList = new ArrayList<>();
    private ArrayAdapter<String> yearadapter;
    private List<String> yearSpinnerArray;
    RelativeLayout progress;
    TextView no_months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        recyclerView = findViewById(R.id.months_recyclerView);
        yearSpinner = findViewById(R.id.yearSpinner);
        progress = findViewById(R.id.progressLoad);
        no_months = findViewById(R.id.noMonths);
        getMonthsAdapter = new GetMonthsAdapter(SummaryActivity.this,mMonthsArrayList);
        recyclerView.setAdapter(getMonthsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(SummaryActivity.this,getResources().getInteger(R.integer.product_grid_span)));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (!isNetworkAvailable()){
            Toast.makeText(SummaryActivity.this,"Check your nectwork connection",Toast.LENGTH_SHORT).show();
        }
        yearSpinnerArray = new ArrayList<>();

        yearadapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, yearSpinnerArray);

        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(yearadapter);

        viewYears();
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showProgress();
                String year= adapterView.getItemAtPosition(i).toString();
                mMonthsArrayList.clear();
                Call<List<GetMonthsModel>> call = RetrofitClient.getInstance(SummaryActivity.this)
                        .getApiConnector()
                        .getMonths(year);
                call.enqueue(new Callback<List<GetMonthsModel>>() {
                    @Override
                    public void onResponse(Call<List<GetMonthsModel>> call, Response<List<GetMonthsModel>> response) {
                        hideProgress();
                        if(response.code()==200){
                             mMonthsArrayList.addAll(response.body());
                            getMonthsAdapter.notifyDataSetChanged();
                            if (mMonthsArrayList.size()<1){
                                no_months.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        }
                        else{
                            Toast.makeText(SummaryActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<GetMonthsModel>> call, Throwable t) {
                        hideProgress();
                        Toast.makeText(SummaryActivity.this,t.getMessage() + "hhhhhd",Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void viewYears() {
        Call<List<GetYearsModel>> call = RetrofitClient.getInstance(SummaryActivity.this)
                .getApiConnector()
                .getYears();
        call.enqueue(new Callback<List<GetYearsModel>>() {
            @Override
            public void onResponse(Call<List<GetYearsModel>> call, Response<List<GetYearsModel>> response) {
                if(response.code()==200){

                    for(int index= 0;index<response.body().size();index++){
                        yearSpinnerArray.add(response.body().get(index).getYear());
                    }
                    yearadapter.notifyDataSetChanged();
                    if (yearSpinnerArray.size()<1){
                        no_months.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
                else{
                    Toast.makeText(SummaryActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetYearsModel>> call, Throwable t) {
                Toast.makeText(SummaryActivity.this,t.getMessage() + "hhhhhd",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }
}

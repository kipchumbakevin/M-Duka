package com.example.shopkipa.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        recyclerView = findViewById(R.id.sales_recyclerView);
        yearSpinner = findViewById(R.id.yearSpinner);
        getMonthsAdapter = new GetMonthsAdapter(SummaryActivity.this,mMonthsArrayList);
        recyclerView.setAdapter(getMonthsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(SummaryActivity.this,1));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
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
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
                String year= adapterView.getItemAtPosition(i).toString();
                mMonthsArrayList.clear();
                Call<List<GetMonthsModel>> call = RetrofitClient.getInstance(SummaryActivity.this)
                        .getApiConnector()
                        .getMonths(year);
                call.enqueue(new Callback<List<GetMonthsModel>>() {
                    @Override
                    public void onResponse(Call<List<GetMonthsModel>> call, Response<List<GetMonthsModel>> response) {
                        if(response.code()==200){
                             mMonthsArrayList.addAll(response.body());
                            getMonthsAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(SummaryActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<GetMonthsModel>> call, Throwable t) {
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
}

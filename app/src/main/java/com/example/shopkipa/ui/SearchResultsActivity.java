package com.example.shopkipa.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.ItemsInTypeAdapter;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.GetTypesInCategoryModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ItemsInTypeAdapter itemsInTypeAdapter;
    String name;
    private static ArrayList<GetStockInTypeModel> mStockArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        recyclerView = findViewById(R.id.search_results_recyclerview);
        itemsInTypeAdapter = new ItemsInTypeAdapter(SearchResultsActivity.this,mStockArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchResultsActivity.this, getResources().getInteger(R.integer.product_grid_span)));
        recyclerView.setAdapter(itemsInTypeAdapter);
        name = "kk";

        viewResults();
    }

    private void viewResults() {
        mStockArrayList.clear();
        Call<List<GetStockInTypeModel>> call2 = RetrofitClient.getInstance(SearchResultsActivity.this)
                .getApiConnector()
                .getSearchResults(name);
        call2.enqueue(new Callback<List<GetStockInTypeModel>>() {
            @Override
            public void onResponse(Call<List<GetStockInTypeModel>> call2, Response<List<GetStockInTypeModel>> response) {
                if (response.code() == 200) {
                    mStockArrayList.addAll(response.body());
                    itemsInTypeAdapter.notifyDataSetChanged();

                } else {

                }
            }

            @Override
            public void onFailure(Call<List<GetStockInTypeModel>> call2, Throwable t) {
                Toast.makeText(SearchResultsActivity.this, t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
            }

        });

}
}

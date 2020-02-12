package com.example.shopkipa.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GivenStockActivity extends AppCompatActivity {
    TabLayout tabLayout;
    RelativeLayout progressLyt;
    ImageView go_back;
    private List<GetCategoriesModel> categories = new ArrayList<>();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_given_stock);
        tabLayout = findViewById(R.id.cart_tab);
        go_back = findViewById(R.id.goBack);
        getCategoryList();

        if (!categories.isEmpty()){
            String tabIndex = categories.get(0).getName();
            getTabContent(tabIndex);
        }
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GivenStockActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabId = (String) tab.getText();
                getTabContent(tabId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void getTabContent(String tabIndex){
        GivenStockFragment givenStockFragment = GivenStockFragment.newInstance(tabIndex);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragment, givenStockFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    private void getCategoryList() {
        ArrayList<GetCategoriesModel> mCategoriesArray;
        categories.clear();
        Call<List<GetCategoriesModel>> call = RetrofitClient.getInstance(mContext)
                .getApiConnector()
                .getAllCategories();
        call.enqueue(new Callback<List<GetCategoriesModel>>() {
            @Override
            public void onResponse(Call<List<GetCategoriesModel>> call, Response<List<GetCategoriesModel>> response) {
                if(response.code()==200){
                    categories.addAll(response.body());
                    filltabs(tabLayout);

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<GetCategoriesModel>> call, Throwable t) {
            }

        });
    }

    private void filltabs(TabLayout tabLayout) {
        if (!categories.isEmpty()){
            for(int index = 0; index<categories.size();index++){
                String fragmentname = categories.get(index).getName();
                tabLayout.addTab(tabLayout.newTab().setText(fragmentname));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GivenStockActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}

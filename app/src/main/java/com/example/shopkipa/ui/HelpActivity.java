package com.example.shopkipa.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.GetFaqsAdapter;
import com.example.shopkipa.models.GetFaqsModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GetFaqsAdapter getFaqsAdapter;
    ArrayList<GetFaqsModel>mFaqsArrayList = new ArrayList<>();
    TextView sendMessage;
    String phone="0706459189";
    RelativeLayout progress,call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        recyclerView = findViewById(R.id.faqs_recyclerView);
        progress = findViewById(R.id.progressLoad);
        sendMessage = findViewById(R.id.send_message);
        call = findViewById(R.id.call);
        getFaqsAdapter = new GetFaqsAdapter(HelpActivity.this,mFaqsArrayList);
        recyclerView.setAdapter(getFaqsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,getResources().getInteger(R.integer.product_grid_span)));

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this,SendMessage.class);
                startActivity(intent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ phone));
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (!isNetworkAvailable()){
            Toast.makeText(HelpActivity.this,"Check your connection",Toast.LENGTH_SHORT).show();
        }else{
            fetchFaqs();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void fetchFaqs() {
        showProgress();
        Call<List<GetFaqsModel>> call = RetrofitClient.getInstance(HelpActivity.this)
                .getApiConnector()
                .getFaqs();
        call.enqueue(new Callback<List<GetFaqsModel>>() {
            @Override
            public void onResponse(Call<List<GetFaqsModel>> call, Response<List<GetFaqsModel>> response) {
                hideProgress();
                if(response.code()==200){
                     mFaqsArrayList.addAll(response.body());
                     getFaqsAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(HelpActivity.this,"Internal server error. Please retry",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetFaqsModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(HelpActivity.this,"Network error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        progress.setVisibility(View.GONE);
    }
}

package com.example.shopkipa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.GetFaqsAdapter;
import com.example.shopkipa.models.GetFaqsModel;
import com.example.shopkipa.networking.RetrofitClient;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        recyclerView = findViewById(R.id.faqs_recyclerView);
        sendMessage = findViewById(R.id.send_message);
        getFaqsAdapter = new GetFaqsAdapter(HelpActivity.this,mFaqsArrayList);
        recyclerView.setAdapter(getFaqsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this,SendMessage.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Help center");
        fetchFaqs();
    }

    private void fetchFaqs() {
        Call<List<GetFaqsModel>> call = RetrofitClient.getInstance(HelpActivity.this)
                .getApiConnector()
                .getFaqs();
        call.enqueue(new Callback<List<GetFaqsModel>>() {
            @Override
            public void onResponse(Call<List<GetFaqsModel>> call, Response<List<GetFaqsModel>> response) {
                if(response.code()==200){
                     mFaqsArrayList.addAll(response.body());
                     getFaqsAdapter.notifyDataSetChanged();
                }
                else{
                }

            }

            @Override
            public void onFailure(Call<List<GetFaqsModel>> call, Throwable t) {
            }
        });
    }


}

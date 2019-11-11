package com.example.shopkipa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.models.ChangePhoneModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneNumber extends AppCompatActivity {
    EditText oldPhone,newPhone;
    Button submit;
    SharedPreferencesConfig sharedPreferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);
        oldPhone = findViewById(R.id.enter_old_phone);
        newPhone = findViewById(R.id.enter_new_phone);
        submit = findViewById(R.id.submit);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitChanges();
            }
        });
    }

    private void submitChanges() {
        String oldphone = oldPhone.getText().toString();
        String newphone = newPhone.getText().toString();
        Call<ChangePhoneModel> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .changePhone(oldphone,newphone);
        call.enqueue(new Callback<ChangePhoneModel>() {
            @Override
            public void onResponse(Call<ChangePhoneModel> call, Response<ChangePhoneModel> response) {
                if(response.code()==201){
                    Toast.makeText(ChangePhoneNumber.this,response.message(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ChangePhoneNumber.this,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangePhoneModel> call, Throwable t) {
                Toast.makeText(ChangePhoneNumber.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

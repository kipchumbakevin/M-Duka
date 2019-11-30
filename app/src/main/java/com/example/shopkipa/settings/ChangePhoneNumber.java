package com.example.shopkipa.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.GenerateCodeModel;
import com.example.shopkipa.models.SignUpMessagesModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneNumber extends AppCompatActivity {
    EditText oldPhone,newPhone,enterPass;
    Button submit;
    String newnumber,oldphone,pas;
    RelativeLayout progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);
        oldPhone = findViewById(R.id.enter_old_phone);
        newPhone = findViewById(R.id.enter_new_phone);
        submit = findViewById(R.id.submit);
        enterPass = findViewById(R.id.enter_password);
        progress = findViewById(R.id.progressLoad);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate();
            }
        });
    }

    private void generate() {
        showProgress();
        oldphone = oldPhone.getText().toString();
        pas = enterPass.getText().toString();
        newnumber = newPhone.getText().toString();
        Call<SignUpMessagesModel> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .checkIfNoCorrect(oldphone,pas);
        call.enqueue(new Callback<SignUpMessagesModel>() {
            @Override
            public void onResponse(Call<SignUpMessagesModel> call, Response<SignUpMessagesModel> response) {
                hideProgress();
                if(response.code()==201){
                    Intent intent = new Intent(ChangePhoneNumber.this,ConfirmPhoneChangeCode.class);
                    intent.putExtra("OLDPHONE",oldphone);
                    intent.putExtra("NEWPHONE",newnumber);
                    startActivity(intent);
                    finish();
                    Toast.makeText(ChangePhoneNumber.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ChangePhoneNumber.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SignUpMessagesModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePhoneNumber.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }
}

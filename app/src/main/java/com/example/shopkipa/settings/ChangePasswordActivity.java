package com.example.shopkipa.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.auth.LoginActivity;
import com.example.shopkipa.R;
import com.example.shopkipa.models.ChangePasswordModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldpass,newpass;
    Button changePass;
    RelativeLayout progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.new_password);
        changePass = findViewById(R.id.change_pass);
        progress = findViewById(R.id.progressLoad);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        showProgress();
        String oldPass = oldpass.getText().toString();
        String newPass = newpass.getText().toString();
        Call<ChangePasswordModel> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .changePassword(newPass,oldPass);
        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                hideProgress();
                if(response.code()==201){
                    Toast.makeText(ChangePasswordActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePasswordActivity.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
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

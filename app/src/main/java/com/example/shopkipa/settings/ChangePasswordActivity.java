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
import com.example.shopkipa.models.ChangedForgotPassModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.MainActivity;
import com.example.shopkipa.utils.Constants;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldpass,newpass,confirmnewpass;
    Button changePass;
    RelativeLayout progress;
    private String clientsFirstName,clientsLastName,clientsUsername,clientsPhone,clientsLocation,token;
    private SharedPreferencesConfig sharedPreferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.new_password);
        confirmnewpass = findViewById(R.id.confirm_new_password);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
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
        String oldPass = oldpass.getText().toString();
        String newPass = newpass.getText().toString();
        String conf = confirmnewpass.getText().toString();
        if (!newPass.equals(conf)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }else {
            showProgress();
            Call<ChangedForgotPassModel> call = RetrofitClient.getInstance(this)
                    .getApiConnector()
                    .changePassword(newPass, oldPass);
            call.enqueue(new Callback<ChangedForgotPassModel>() {
                @Override
                public void onResponse(Call<ChangedForgotPassModel> call, Response<ChangedForgotPassModel> response) {
                    hideProgress();
                    if (response.code()==201) {
                        Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        token = response.body().getAccessToken();
                        clientsFirstName = response.body().getUser().getFirstName();
                        clientsLastName = response.body().getUser().getLastName();
                        clientsLocation = response.body().getUser().getLocation();
                        clientsUsername = response.body().getUser().getUsername();
                        clientsPhone = response.body().getUser().getPhone();
                        sharedPreferencesConfig.saveAuthenticationInformation(token, clientsFirstName, clientsLastName, clientsLocation, clientsUsername, clientsPhone, Constants.ACTIVE_CONSTANT);
                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Your old password is incorrect", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ChangedForgotPassModel> call, Throwable t) {
                    hideProgress();
                    Toast.makeText(ChangePasswordActivity.this, "errot:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

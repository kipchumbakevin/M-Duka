package com.example.shopkipa.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.SendSignUpCode;
import com.example.shopkipa.models.SignUpModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeAfterSignUpActivity extends AppCompatActivity {
    EditText enter_code;
    Button confirm;
    String first_name,last_name,username,phone,location,code,password,confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_after_sign_up);
        enter_code = findViewById(R.id.enter_signup_code);
        confirm = findViewById(R.id.confirm_signup_code);

        phone = getIntent().getExtras().getString("NUMBER");
        first_name = getIntent().getExtras().getString("FIRST");
        last_name = getIntent().getExtras().getString("LAST");
        username = getIntent().getExtras().getString("USER");
        location = getIntent().getExtras().getString("LOCATION");
        password = getIntent().getExtras().getString("PASS");
        confirmPassword = getIntent().getExtras().getString("CONFIRM");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmCode();
            }
        });
    }

    private void confirmCode() {
            code = enter_code.getText().toString();
            Call<SignUpModel> call = RetrofitClient.getInstance(CodeAfterSignUpActivity.this)
                    .getApiConnector()
                    .signUp(first_name,last_name,username,location,phone,password,confirmPassword,code);
            call.enqueue(new Callback<SignUpModel>() {
                @Override
                public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                    if(response.code()==201){
                        Toast.makeText(CodeAfterSignUpActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CodeAfterSignUpActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(CodeAfterSignUpActivity.this,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<SignUpModel> call, Throwable t) {
                    Toast.makeText(CodeAfterSignUpActivity.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
    }
}

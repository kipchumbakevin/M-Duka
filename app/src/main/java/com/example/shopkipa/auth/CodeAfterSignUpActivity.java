package com.example.shopkipa.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.ConfirmSignUpCode;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeAfterSignUpActivity extends AppCompatActivity {
    EditText enter_code;
    Button confirm;
    String numb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_after_sign_up);
        enter_code = findViewById(R.id.enter_signup_code);
        confirm = findViewById(R.id.confirm_signup_code);

        numb = getIntent().getExtras().getString("NUMBER");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CodeAfterSignUpActivity.this,numb,Toast.LENGTH_LONG).show();
                confirmCode();
            }
        });
    }

    private void confirmCode() {
            final String code = enter_code.getText().toString();
            Call<ConfirmSignUpCode> call = RetrofitClient.getInstance(CodeAfterSignUpActivity.this)
                    .getApiConnector()
                    .signUpCode(numb,code);
            call.enqueue(new Callback<ConfirmSignUpCode>() {
                @Override
                public void onResponse(Call<ConfirmSignUpCode> call, Response<ConfirmSignUpCode> response) {
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
                public void onFailure(Call<ConfirmSignUpCode> call, Throwable t) {
                    Toast.makeText(CodeAfterSignUpActivity.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
    }
}

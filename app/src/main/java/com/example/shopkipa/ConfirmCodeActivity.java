package com.example.shopkipa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.models.ConfirmCodeModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCodeActivity extends AppCompatActivity {
    EditText enterCode;
    Button confirmCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        enterCode = findViewById(R.id.enter_code);
        confirmCode = findViewById(R.id.confirmCode);

        confirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
    }

    private void confirm() {
        String code = enterCode.getText().toString();
        Call<ConfirmCodeModel> call = RetrofitClient.getInstance(ConfirmCodeActivity.this)
                .getApiConnector()
                .confirm(code);
        call.enqueue(new Callback<ConfirmCodeModel>() {
            @Override
            public void onResponse(Call<ConfirmCodeModel> call, Response<ConfirmCodeModel> response) {
                if(response.code()==201){
                    Toast.makeText(ConfirmCodeActivity.this,"imeconfirmiwa",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ConfirmCodeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ConfirmCodeActivity.this,response.message()+"response",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ConfirmCodeModel> call, Throwable t) {
                Toast.makeText(ConfirmCodeActivity.this,t.getMessage()+"error",Toast.LENGTH_LONG).show();
            }
        });
    }
}

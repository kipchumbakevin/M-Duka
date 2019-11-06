package com.example.shopkipa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.models.SignUpModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button login,register;
    TextView forgotPassword;
    EditText firstName,lastName,userName,phoneNumber,pass,confirmPass,locationUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login = findViewById(R.id.login);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        userName = findViewById(R.id.user_name);
        phoneNumber = findViewById(R.id.phone);
        pass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirm_password);
        locationUser = findViewById(R.id.location);
        forgotPassword = findViewById(R.id.forgotPassword);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String firstname,lastname,username,location,phone,password,confirmPassword;
        firstname = firstName.getText().toString();
        lastname = lastName.getText().toString();
        username = userName.getText().toString();
        location = locationUser.getText().toString();
        phone = phoneNumber.getText().toString();
        password = pass.getText().toString();
        confirmPassword = confirmPass.getText().toString();


        Call<SignUpModel> call = RetrofitClient.getInstance(SignUpActivity.this)
                .getApiConnector()
                .signUp(firstname,lastname,username,location,phone,password,confirmPassword);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                if(response.code()==201){
                    Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this,response.message()+"response",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                Toast.makeText(SignUpActivity.this,t.getMessage()+"error",Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.shopkipa.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.SendCodeModel;
import com.example.shopkipa.models.SendSignUpCode;
import com.example.shopkipa.models.SignUpMessagesModel;
import com.example.shopkipa.models.SignUpModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button login,register;
    RelativeLayout progressLyt;
    TextView forgotPassword;
    EditText firstName,lastName,userName,phoneNumber,pass,confirmPass,locationUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login = findViewById(R.id.login);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        progressLyt = findViewById(R.id.progressLoad);;
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
                if (isErrors()){
                    Toast.makeText(SignUpActivity.this,"Please ensure you fill all fields",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser();
                }
            }
        });
    }
    public boolean isErrors(){
        String firstname,lastname,username,phone,passw,confirm;
        firstname = firstName.getText().toString();
        lastname= lastName.getText().toString();
        username = userName.getText().toString();
        phone = phoneNumber.getText().toString();
        passw = pass.getText().toString();
        confirm = confirmPass.getText().toString();
        if (TextUtils.isEmpty(firstname)) {
            firstName.setError("This is a required field");
            return true;}
        if (TextUtils.isEmpty(lastname)) {
            lastName.setError("This is a required field");
            return true;
        }
        if (TextUtils.isEmpty(username)) {
            userName.setError("This is a required field");
            return true;}

        if (TextUtils.isEmpty(phone)) {
            phoneNumber.setError("This is a required field");
            return true;}
        if (TextUtils.isEmpty(passw)) {
            pass.setError("This is a required field");
            return true;}
        if (TextUtils.isEmpty(confirm)) {
            confirmPass.setError("This is a required field");
            return true;}
        else {
            return false;
        }
    }
    private void registerUser() {
        final String firstname,lastname,username,location,phone,password,confirmPassword;
        firstname = firstName.getText().toString();
        lastname = lastName.getText().toString();
        username = userName.getText().toString();
        location = locationUser.getText().toString();
        phone = phoneNumber.getText().toString();
        password = pass.getText().toString();
        confirmPassword = confirmPass.getText().toString();
        if (!password.equals(confirmPassword)){
            Toast.makeText(SignUpActivity.this,"Passwords do not match",Toast.LENGTH_LONG).show();
        }else {
            showProgress();
            Call<SignUpMessagesModel> call = RetrofitClient.getInstance(SignUpActivity.this)
                    .getApiConnector()
                    .checkIfExist(phone, username);
            call.enqueue(new Callback<SignUpMessagesModel>() {
                @Override
                public void onResponse(Call<SignUpMessagesModel> call, Response<SignUpMessagesModel> response) {
                     hideProgress();
                    if (response.code() == 201) {
                        Intent intent = new Intent(SignUpActivity.this, CodeAfterSignUpActivity.class);
                        intent.putExtra("FIRST", firstname);
                        intent.putExtra("LAST", lastname);
                        intent.putExtra("USER", username);
                        intent.putExtra("LOCATION", location);
                        intent.putExtra("NUMBER", phone);
                        intent.putExtra("PASS", password);
                        intent.putExtra("CONFIRM", confirmPassword);
                        startActivity(intent);
                        finish();
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<SignUpMessagesModel> call, Throwable t) {
                     hideProgress();
                    Toast.makeText(SignUpActivity.this, t.getMessage() + "error", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}

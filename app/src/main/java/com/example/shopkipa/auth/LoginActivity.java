package com.example.shopkipa.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.UsersModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.MainActivity;
import com.example.shopkipa.utils.Constants;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button signup,login;
    EditText userName,pass;
    TextView forgotPass;
    private String clientsFirstName,clientsLastName,clientsUsername,clientsPhone,clientsLocation,token;
    private SharedPreferencesConfig sharedPreferencesConfig;
    RelativeLayout progressLyt;


    @Override
    protected void onResume() {
        super.onResume();
//        if (sharedPreferencesConfig.isloggedIn()){
//            welcome();
//        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signUp);
        login = findViewById(R.id.login);
        forgotPass = findViewById(R.id.forgotPassword);
        userName = findViewById(R.id.user_name);
        progressLyt = findViewById(R.id.progressLoad);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        pass = findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
                if (userName.getText().toString().isEmpty()){
                    userName.setError("Username required");
                }if (pass.getText().toString().isEmpty()){
                    pass.setError("Password required");
                }else{
                    loginUser();
                }
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser() {
        showProgress();
        String username = userName.getText().toString();
        String password = pass.getText().toString();
        Call<UsersModel> call = RetrofitClient.getInstance(LoginActivity.this)
                .getApiConnector()
                .login(username,password);
        call.enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                hideProgress();
                if(response.isSuccessful()){
                    token = response.body().getAccessToken();
                    Log.d("token", token);
                    clientsFirstName = response.body().getUser().getFirstName();
                    clientsLastName = response.body().getUser().getLastName();
                    clientsLocation = response.body().getUser().getLocation();
                    clientsUsername = response.body().getUser().getUsername();
                    clientsPhone = response.body().getUser().getPhone();
                    sharedPreferencesConfig.saveAuthenticationInformation(token,clientsFirstName,clientsLastName,clientsLocation,clientsUsername,clientsPhone, Constants.ACTIVE_CONSTANT);
                    welcome();
                }
                else{
                    Toast.makeText(LoginActivity.this,response.message()+"response",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(LoginActivity.this,t.getMessage()+"error",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void welcome(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}

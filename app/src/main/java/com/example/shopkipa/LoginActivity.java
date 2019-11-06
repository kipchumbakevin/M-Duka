package com.example.shopkipa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.models.LoginModel;
import com.example.shopkipa.models.UsersModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.Constants;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button signup,login;
    EditText userName,pass;
    public String clientsFirstName,clientsLastName,clientsUsername,clientsPhone,clientsLocation,accessToken;
    private SharedPreferencesConfig sharedPreferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signUp);
        login = findViewById(R.id.login);
        userName = findViewById(R.id.user_name);
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
                loginUser();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferencesConfig.isloggedIn()){
            welcome();
        }
    }

    private void loginUser() {
        String username = userName.getText().toString();
        String password = pass.getText().toString();
        Call<UsersModel> call = RetrofitClient.getInstance(LoginActivity.this)
                .getApiConnector()
                .login(username,password);
        call.enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                if(response.isSuccessful()){
                    accessToken = response.body().getAccessToken();
                    Log.d("token", accessToken);
                    clientsFirstName = response.body().getUser().getFirstName();
                    clientsLastName = response.body().getUser().getLastName();
                    clientsLocation = response.body().getUser().getLocation();
                    clientsUsername = response.body().getUser().getUsername();
                    clientsPhone = response.body().getUser().getPhone();
                    sharedPreferencesConfig.saveAuthenticationInformation(accessToken,clientsUsername,clientsPhone,clientsFirstName,clientsLastName,clientsLocation, Constants.ACTIVE_CONSTANT);
                    welcome();
                }
                else{
                    Toast.makeText(LoginActivity.this,response.message()+"response",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage()+"error",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void welcome(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

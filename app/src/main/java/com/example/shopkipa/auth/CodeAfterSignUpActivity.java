package com.example.shopkipa.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.SendSignUpCode;
import com.example.shopkipa.models.SignUpModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.receivers.AppSignatureHashHelper;
import com.example.shopkipa.receivers.ZikySMSReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeAfterSignUpActivity extends AppCompatActivity implements
        ZikySMSReceiver.OTPReceiveListener {
    EditText enter_code;
    Button confirm;
    String first_name,last_name,username,phone,location,code,password,confirmPassword;
    private ZikySMSReceiver smsReceiver;
    private Boolean reset = false;
    public String appSignature;
    private Context context;

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
                registerUserAfterConfirmation();
            }
        });
        context = getApplicationContext();
        appSignature = new AppSignatureHashHelper(this).getAppSignatures().get(0);
        sendSignCode();
    }
    private void sendSignCode() {
        Toast.makeText(CodeAfterSignUpActivity.this,appSignature,Toast.LENGTH_LONG).show();
        //showProgress();
        Call<SendSignUpCode> call = RetrofitClient.getInstance(CodeAfterSignUpActivity.this)
                .getApiConnector()
                .signUpCode(phone,appSignature);
        call.enqueue(new Callback<SendSignUpCode>() {
            @Override
            public void onResponse(Call<SendSignUpCode> call, Response<SendSignUpCode> response) {
               // hideProgress();
                if(response.code()==201){
                    startSMSListener();
                    startCountDownTimer();
                    Toast.makeText(CodeAfterSignUpActivity.this,"sent",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(CodeAfterSignUpActivity.this,response.message()+"response",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SendSignUpCode> call, Throwable t) {
               // hideProgress();
                Toast.makeText(CodeAfterSignUpActivity.this,t.getMessage()+"error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerUserAfterConfirmation() {
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
    private void startSMSListener() {
        try {
            smsReceiver = new ZikySMSReceiver();
            smsReceiver.setOTPListener(CodeAfterSignUpActivity.this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // API successfully started
                    Log.d("OTP-API:", "Successfully Started");
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail to start API
                    //Reload Activity or what?

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startCountDownTimer(){

        new CountDownTimer(60000, 1000) { // 60 seconds, in 1 second intervals
            public void onTick(long millisUntilFinished) {
             //   textViewResendSMS.setText("Resend SMS after "+millisUntilFinished / 1000 +" Seconds");
             //   textViewResendSMS.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
             //   textViewResendSMS.setText("Resend SMS");
             //   resendSMS.setV

            }
        }.start();
    }
    @Override
    public void onOTPReceived(String verificationSMS) {
        //success
        Log.d("OTP-SUCCESS:", verificationSMS);
        Toast.makeText(this, "OTP Success", Toast.LENGTH_SHORT).show();

        //EXTRACT THE 5-digit Code
        enter_code.setText(extractCodeFromSMS(verificationSMS));

        //Start Verification too :)
        registerUserAfterConfirmation();
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
    }

    private void verification() {
        if (!reset){
            registerUserAfterConfirmation();
        }else{

        }
    }

    @Override
    public void onOTPTimeOut() {
        Log.d("OTP-TIMEOUT:", "TIMEOUT");
        Toast.makeText(this, "TIME-OUT, Try Again", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onOTPReceivedError(String error) {
        //error
        Log.d("OTP-ERROR:", error);
        Toast.makeText(this, "Error: "+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
    }

    public String extractCodeFromSMS(String verificationSMS) {
        return verificationSMS.split(":")[1];
    }
    public void resendSMS() {
       // shortCodeEditText.setText(""); // Clear Code
        sendSignCode();
    }
}

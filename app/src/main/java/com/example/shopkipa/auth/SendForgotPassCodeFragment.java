package com.example.shopkipa.auth;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.ForgotPasswordModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendForgotPassCodeFragment extends Fragment {
    TextView backToLogin;
    EditText phoneNumber,enterNewPass,confirmNewPass;
    Button confirmPassChange;
    String newpassword;
    RelativeLayout progress;

    public SendForgotPassCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_forgot_pass_code, container, false);
        backToLogin = view.findViewById(R.id.back_to_login);
        phoneNumber = view.findViewById(R.id.phone_number);
        enterNewPass = view.findViewById(R.id.new_password);
        confirmNewPass = view.findViewById(R.id.confirm_new_password);
        confirmPassChange = view.findViewById(R.id.change_pass);
        progress = view.findViewById(R.id.progressLoad);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        confirmPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
        return view;
    }

    private void changePass() {
        showProgress();
        String phone = phoneNumber.getText().toString();
        Call<ForgotPasswordModel> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .sendForgotCode(phone);
        call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                hideProgress();
                if(response.code()==201){
                    newpassword = enterNewPass.getText().toString();
                    Toast.makeText(getActivity(),response.message(), Toast.LENGTH_SHORT).show();
                    changeFragment();
                }
                else{
                    Toast.makeText(getActivity(),"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(),"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeFragment() {
        NewPassForgotFragment fragment = new NewPassForgotFragment();
        Bundle newpass = new Bundle();
        newpass.putString("NEW_PASS",newpassword);
        fragment.setArguments(newpass);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragments,fragment,fragment.getTag()).commit();
    }
    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

}

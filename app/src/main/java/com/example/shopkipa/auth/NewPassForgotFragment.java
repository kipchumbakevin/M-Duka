package com.example.shopkipa.auth;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.ChangedForgotPassModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.ui.MainActivity;
import com.example.shopkipa.utils.Constants;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPassForgotFragment extends Fragment {
    EditText enterCodeSent;
    Button confirmCodeSent;
    String newpass;
    RelativeLayout progress;
    private String clientsFirstName,clientsLastName,clientsUsername,clientsPhone,clientsLocation,token;
    private SharedPreferencesConfig sharedPreferencesConfig;


    public NewPassForgotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_pass_forgot, container, false);
        enterCodeSent = view.findViewById(R.id.code_sent);
        confirmCodeSent = view.findViewById(R.id.confirm_code_sent);
        progress = view.findViewById(R.id.progressLoad);
        sharedPreferencesConfig = new SharedPreferencesConfig(getActivity());
        newpass= getArguments().getString("NEW_PASS");

        confirmCodeSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
        return view;
    }

    private void confirm() {
        showProgress();
        String code = enterCodeSent.getText().toString();
        Call<ChangedForgotPassModel> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .newPass(code,newpass);
        call.enqueue(new Callback<ChangedForgotPassModel>() {
            @Override
            public void onResponse(Call<ChangedForgotPassModel> call, Response<ChangedForgotPassModel> response) {
                hideProgress();
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    token = response.body().getAccessToken();
                    clientsFirstName = response.body().getUser().getFirstName();
                    clientsLastName = response.body().getUser().getLastName();
                    clientsLocation = response.body().getUser().getLocation();
                    clientsUsername = response.body().getUser().getUsername();
                    clientsPhone = response.body().getUser().getPhone();
                    sharedPreferencesConfig.saveAuthenticationInformation(token,clientsFirstName,clientsLastName,clientsLocation,clientsUsername,clientsPhone, Constants.ACTIVE_CONSTANT);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity(),"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangedForgotPassModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(),"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

}

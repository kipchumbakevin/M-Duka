package com.example.shopkipa.settings;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.ConfirmPhoneChangeCodeModel;
import com.example.shopkipa.models.GenerateCodeModel;
import com.example.shopkipa.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmCodeFragment extends Fragment {
    EditText enterCode;
    Button confirmCode;
    String newphone;
    RelativeLayout progress;
    public ConfirmCodeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_code, container, false);
        enterCode = view.findViewById(R.id.enter_pass_code);
        confirmCode = view.findViewById(R.id.submit_code);
        progress = view.findViewById(R.id.progressLoad);
        newphone = getArguments().getString("CODE_NUMBER");

        confirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
        return view;
    }

    private void confirm() {
        showProgress();
        final String code = enterCode.getText().toString();
        Log.d("codes", newphone);
        Call<ConfirmPhoneChangeCodeModel> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .changePhone(newphone,code);
        call.enqueue(new Callback<ConfirmPhoneChangeCodeModel>() {
            @Override
            public void onResponse(Call<ConfirmPhoneChangeCodeModel> call, Response<ConfirmPhoneChangeCodeModel> response) {
                hideProgress();
                if(response.code()==201){
                    Toast.makeText(getActivity(),"Your number has been changed successfully",Toast.LENGTH_SHORT).show();
                    change();
                }
                else{
                    Toast.makeText(getActivity(),"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ConfirmPhoneChangeCodeModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(),"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void change() {
        GenerateCodeFragment fragments = new GenerateCodeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment,fragments,fragments.getTag()).commit();
    }
    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

}

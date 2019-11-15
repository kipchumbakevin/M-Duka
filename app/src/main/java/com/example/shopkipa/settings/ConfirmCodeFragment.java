package com.example.shopkipa.settings;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.ChangePhoneModel;
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

    public ConfirmCodeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_code, container, false);
        enterCode = view.findViewById(R.id.enter_pass_code);
        confirmCode = view.findViewById(R.id.submit_code);

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
        final String code = enterCode.getText().toString();
        Log.d("codes", newphone);
        Call<ChangePhoneModel> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .changePhone(newphone,code);
        call.enqueue(new Callback<ChangePhoneModel>() {
            @Override
            public void onResponse(Call<ChangePhoneModel> call, Response<ChangePhoneModel> response) {
                if(response.code()==201){
                    Toast.makeText(getActivity(),response.message(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangePhoneModel> call, Throwable t) {
                Toast.makeText(getActivity(),"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}

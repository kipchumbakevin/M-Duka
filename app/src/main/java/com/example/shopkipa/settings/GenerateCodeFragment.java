package com.example.shopkipa.settings;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
public class GenerateCodeFragment extends Fragment {

    EditText oldPhone,newPhone,enterPass;
    Button submit;
    String Pnumber;

    public GenerateCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view= inflater.inflate(R.layout.fragment_generate_code, container, false);
        oldPhone = view.findViewById(R.id.enter_old_phone);
        newPhone = view.findViewById(R.id.enter_new_phone);
        submit = view.findViewById(R.id.submit);
        enterPass = view.findViewById(R.id.enter_password);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate();
            }
        });
            return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void generate() {
        String oldphone = oldPhone.getText().toString();
        String pas = enterPass.getText().toString();
        Call<ChangePhoneModel> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .generateCode(oldphone,pas);
        call.enqueue(new Callback<ChangePhoneModel>() {
            @Override
            public void onResponse(Call<ChangePhoneModel> call, Response<ChangePhoneModel> response) {
                if(response.code()==201){
                    oldPhone.getText().clear();
                    enterPass.getText().clear();
                    Pnumber = newPhone.getText().toString();
                    Log.d("ppn", Pnumber);
                    Toast.makeText(getActivity(),response.message(),Toast.LENGTH_SHORT).show();

                    changeFragment();
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

    private void changeFragment() {
        ConfirmCodeFragment fragments = new ConfirmCodeFragment();
        Bundle number = new Bundle();
        number.putString("CODE_NUMBER",Pnumber);
        fragments.setArguments(number);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment,fragments,fragments.getTag()).commit();
    }
}

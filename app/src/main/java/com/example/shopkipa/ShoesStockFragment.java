package com.example.shopkipa;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoesStockFragment extends Fragment {


    private static final String REQUEST_TYPE = "com.example.shopkipa.REQUEST_TYPE";
    CheckBox stockClothes,stockShoes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shoes_stock, container, false);
        return view;
    }
    public static ShoesStockFragment newInstance(int position){
        ShoesStockFragment myStockFragment =new ShoesStockFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(REQUEST_TYPE,position);
        myStockFragment.setArguments(bundle);
        return myStockFragment;
    }

}

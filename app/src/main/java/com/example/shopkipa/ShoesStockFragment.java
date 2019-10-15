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
        stockClothes = view.findViewById(R.id.stockCloth);
        stockShoes = view.findViewById(R.id.stockShoe);
        int requestType=(int)getArguments().get(REQUEST_TYPE);
            if (requestType == 0) {
                Toast.makeText(getActivity(), "men shoes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 1) {
                Toast.makeText(getActivity(), "Ladies shoes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 2) {
                Toast.makeText(getActivity(), "Boys shoes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 3) {
                Toast.makeText(getActivity(), "Girls shoes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 4) {
                Toast.makeText(getActivity(), "Unisex shoes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 5) {
                Toast.makeText(getActivity(), "Other shoes", Toast.LENGTH_SHORT).show();
            }
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

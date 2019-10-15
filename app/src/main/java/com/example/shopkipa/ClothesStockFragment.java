package com.example.shopkipa;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClothesStockFragment extends Fragment {


    private  static final String REQUEST_TYPE = "com.example.shopkipa.REQUEST_TYPE" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clothes_stock, container, false);

        int requestType=(int)getArguments().get(REQUEST_TYPE);
            if (requestType == 0) {
                Toast.makeText(getActivity(), "men clothes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 1) {
                Toast.makeText(getActivity(), "Ladies clothes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 2) {
                Toast.makeText(getActivity(), "Boys clothes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 3) {
                Toast.makeText(getActivity(), "Girls clothes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 4) {
                Toast.makeText(getActivity(), "Unisex clothes", Toast.LENGTH_SHORT).show();
            } else if (requestType == 5) {
                Toast.makeText(getActivity(), "Other clothes", Toast.LENGTH_SHORT).show();
            }
            return view;
        }

    public static ClothesStockFragment newInstance(int position){
        ClothesStockFragment viewCustomerStockFragment =new ClothesStockFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(REQUEST_TYPE,position);
        viewCustomerStockFragment.setArguments(bundle);
        return viewCustomerStockFragment;
    }

}

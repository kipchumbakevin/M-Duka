package com.example.shopkipa;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.adapters.CustomerTabAdapter;
import com.example.shopkipa.adapters.GetStockAdapter;
import com.example.shopkipa.models.GetStockModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClothesStockFragment extends Fragment {


    private  static final String REQUEST_TYPE = "com.example.shopkipa.REQUEST_TYPE" ;
    private static ArrayList<GetStockModel>mStockArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt;
    GetStockAdapter getStockAdapter;
    String fragment_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clothes_stock, container, false);
        recyclerView = view.findViewById(R.id.stock_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        recyclerView.hasFixedSize();
        getStockAdapter = new GetStockAdapter(getActivity(),mStockArrayList);
        recyclerView.setAdapter(getStockAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

//        int requestType=(int)getArguments().get(REQUEST_TYPE);
//            if (requestType == 0) {
//                viewStock();
//               Toast.makeText(getActivity(), "men clothes", Toast.LENGTH_SHORT).show();
//            } else if (requestType == 1) {
//                Toast.makeText(getActivity(), "Ladies clothes", Toast.LENGTH_SHORT).show();
//            } else if (requestType == 2) {
//                Toast.makeText(getActivity(), "Boys clothes", Toast.LENGTH_SHORT).show();
//            } else if (requestType == 3) {
//                Toast.makeText(getActivity(), "Girls clothes", Toast.LENGTH_SHORT).show();
//            } else if (requestType == 4) {
//                Toast.makeText(getActivity(), "Unisex clothes", Toast.LENGTH_SHORT).show();
//            } else if (requestType == 5) {
//                Toast.makeText(getActivity(), "Other clothes", Toast.LENGTH_SHORT).show();
//            }

        viewStock();
            return view;
        }

    private void viewStock() {
        showProgress();
        fragment_name = getArguments().getString("fragment_name",fragment_name);
        Toast.makeText(getActivity(),fragment_name+"YEES",Toast.LENGTH_SHORT).show();
        ArrayList<GetStockModel> mRequestsArray;
        mStockArrayList.clear();
        String categoryname = fragment_name;
        Call<List<GetStockModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getAllStock(categoryname);
        call.enqueue(new Callback<List<GetStockModel>>() {
            @Override
            public void onResponse(Call<List<GetStockModel>> call, Response<List<GetStockModel>> response) {
                hideProgress();
                if(response.code()==200){
                    mStockArrayList.addAll(response.body());
                    getStockAdapter.notifyDataSetChanged();

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<GetStockModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
            }

        });
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }

    public static ClothesStockFragment newInstance(String fragmentname){
        ClothesStockFragment viewCustomerStockFragment =new ClothesStockFragment();
        Bundle bundle=new Bundle();
//        bundle.putInt(REQUEST_TYPE,val);
        bundle.putString("fragment_name",fragmentname);
        viewCustomerStockFragment.setArguments(bundle);
        return viewCustomerStockFragment;
    }

}

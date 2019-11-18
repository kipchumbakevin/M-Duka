package com.example.shopkipa.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.ItemsInTypeAdapter;
import com.example.shopkipa.adapters.RestockAdapter;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.SuggestedRestockModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestedRestockFragment extends Fragment {
    private static ArrayList<SuggestedRestockModel> mStockArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt;
    String fragment_name;
    RestockAdapter restockAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested_restock, container, false);
        recyclerView = view.findViewById(R.id.restock_recyclerView);
        recyclerView.hasFixedSize();

        restockAdapter = new RestockAdapter(getContext(),mStockArrayList);
        recyclerView.setAdapter(restockAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        getRestock();
        return view;
    }

    private void getRestock() {
        fragment_name = getArguments().getString("fragment_name", fragment_name);
        mStockArrayList.clear();
        String category = fragment_name;
        Toast.makeText(getActivity(),category,Toast.LENGTH_LONG).show();
        Call<List<SuggestedRestockModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getSuggestedRestock(category);
        call.enqueue(new Callback<List<SuggestedRestockModel>>() {
            @Override
            public void onResponse(Call<List<SuggestedRestockModel>> call, Response<List<SuggestedRestockModel>> response) {
                if (response.code() == 200) {
                    mStockArrayList.addAll(response.body());
                    restockAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(),response.message()+response.code() + "shiet",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(),response.message()+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SuggestedRestockModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
            }

        });

    }
    public static SuggestedRestockFragment newInstance(String fragmentname){
        SuggestedRestockFragment suggestedRestockFragment =new SuggestedRestockFragment();
        Bundle bundle=new Bundle();
        bundle.putString("fragment_name",fragmentname);
        suggestedRestockFragment.setArguments(bundle);
        return suggestedRestockFragment;
    }

}

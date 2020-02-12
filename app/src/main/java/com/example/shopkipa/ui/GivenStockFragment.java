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
import com.example.shopkipa.adapters.GivenStockAdapter;
import com.example.shopkipa.adapters.ObscoleteStockAdapter;
import com.example.shopkipa.models.ViewGivenStockModel;
import com.example.shopkipa.models.ViewObscoleteStockModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GivenStockFragment extends Fragment {
    private static ArrayList<ViewGivenStockModel> mGiven = new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt, nogiven;
    String fragment_name;
    GivenStockAdapter givenStockAdapter;


    public GivenStockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_given_stock, container, false);
        recyclerView = view.findViewById(R.id.restock_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        nogiven = view.findViewById(R.id.no_given);
        recyclerView.hasFixedSize();
        givenStockAdapter = new GivenStockAdapter(getContext(), mGiven);
        recyclerView.setAdapter(givenStockAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.product_grid_span)));
        viewGiven();
        return view;
    }

    private void viewGiven() {
        showProgress();
        fragment_name = getArguments().getString("fragment_name", fragment_name);
        mGiven.clear();
        String category = fragment_name;
        Call<List<ViewGivenStockModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getGiven(category);
        call.enqueue(new Callback<List<ViewGivenStockModel>>() {
            @Override
            public void onResponse(Call<List<ViewGivenStockModel>> call, Response<List<ViewGivenStockModel>> response) {
                hideProgress();
                if (response.code() == 200) {
                    mGiven.addAll(response.body());
                    givenStockAdapter.notifyDataSetChanged();
                    if (mGiven.size()<1) {
                        nogiven.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getActivity(),response.message()+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViewGivenStockModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
            }

        });
    }
    public static GivenStockFragment newInstance(String fragmentname){
        GivenStockFragment givenStockFragment =new GivenStockFragment();
        Bundle bundle=new Bundle();
        bundle.putString("fragment_name",fragmentname);
        givenStockFragment.setArguments(bundle);
        return givenStockFragment;
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }

}

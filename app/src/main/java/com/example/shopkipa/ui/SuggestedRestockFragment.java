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
    private static ArrayList<SuggestedRestockModel> mStockArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt, noProducts;
    String fragment_name;
    RestockAdapter restockAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested_restock, container, false);
        recyclerView = view.findViewById(R.id.restock_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        noProducts = view.findViewById(R.id.no_suggested_products_view);
        recyclerView.hasFixedSize();
        restockAdapter = new RestockAdapter(getContext(), mStockArrayList);
        recyclerView.setAdapter(restockAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.product_grid_span)));

        getRestock();
        return view;
    }

    private void getRestock() {
        showProgress();
        fragment_name = getArguments().getString("fragment_name", fragment_name);
        mStockArrayList.clear();
        String category = fragment_name;
        Call<List<SuggestedRestockModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getSuggestedRestock(category);
        call.enqueue(new Callback<List<SuggestedRestockModel>>() {
            @Override
            public void onResponse(Call<List<SuggestedRestockModel>> call, Response<List<SuggestedRestockModel>> response) {
                hideProgress();
                if (response.code() == 200) {
                    mStockArrayList.addAll(response.body());
                    restockAdapter.notifyDataSetChanged();
                    if (mStockArrayList.size() < 1) {
                        noProducts.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getActivity(), response.message() + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SuggestedRestockModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
            }

        });

    }

    public static SuggestedRestockFragment newInstance(String fragmentname) {
        SuggestedRestockFragment suggestedRestockFragment = new SuggestedRestockFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragment_name", fragmentname);
        suggestedRestockFragment.setArguments(bundle);
        return suggestedRestockFragment;
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}

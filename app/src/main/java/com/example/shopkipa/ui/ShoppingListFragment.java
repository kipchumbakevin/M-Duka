package com.example.shopkipa.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.ShoppingListAdapter;
import com.example.shopkipa.adapters.ViewAdsAdapter;
import com.example.shopkipa.models.ViewAdsModel;
import com.example.shopkipa.models.ViewShoppingListModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {
    private static ArrayList<ViewShoppingListModel> mShopping = new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt, noshopping;
    String fragment_name;
    ShoppingListAdapter shoppingListAdapter;
    private ArrayList<ViewAdsModel> mAdsArray=new ArrayList<>();
    ViewAdsAdapter viewAdsAdapter;
    RecyclerView recyclerView2;

    public ShoppingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        recyclerView = view.findViewById(R.id.restock_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        noshopping = view.findViewById(R.id.no_shoppinglist);
        recyclerView.hasFixedSize();
        shoppingListAdapter = new ShoppingListAdapter(getContext(), mShopping);
        recyclerView.setAdapter(shoppingListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.product_grid_span)));
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        viewAdsAdapter = new ViewAdsAdapter(getActivity(),mAdsArray);
        recyclerView2 = view.findViewById(R.id.ads_RecyclerView);
        recyclerView2.setAdapter(viewAdsAdapter);
        recyclerView2.setLayoutManager(linearLayoutManager);
        viewShoppingList();
        viewAds();
        return view;
    }

    private void viewShoppingList() {
        showProgress();
        fragment_name = getArguments().getString("fragment_name", fragment_name);
        mShopping.clear();
        String category = fragment_name;
        Call<List<ViewShoppingListModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getShoppingList(category);
        call.enqueue(new Callback<List<ViewShoppingListModel>>() {
            @Override
            public void onResponse(Call<List<ViewShoppingListModel>> call, Response<List<ViewShoppingListModel>> response) {
                hideProgress();
                if (response.code() == 200) {
                    mShopping.addAll(response.body());
                    shoppingListAdapter.notifyDataSetChanged();
                    if (mShopping.size()<1) {
                        noshopping.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getActivity(),response.message()+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViewShoppingListModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
            }

        });

    }

    public static ShoppingListFragment newInstance(String fragmentname){
        ShoppingListFragment shoppingListFragment =new ShoppingListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("fragment_name",fragmentname);
        shoppingListFragment.setArguments(bundle);
        return shoppingListFragment;
    }
    private void viewAds() {
        mAdsArray.clear();
        Call<List<ViewAdsModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getAds();
        call.enqueue(new Callback<List<ViewAdsModel>>() {
            @Override
            public void onResponse(Call<List<ViewAdsModel>> call, Response<List<ViewAdsModel>> response) {
                hideProgress();
                if (response.code() == 200) {
                    mAdsArray.addAll(response.body());
                    viewAdsAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(),response.message()+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViewAdsModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}

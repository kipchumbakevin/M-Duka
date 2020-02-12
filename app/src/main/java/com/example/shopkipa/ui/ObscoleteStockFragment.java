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
import com.example.shopkipa.adapters.ObscoleteStockAdapter;
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
public class ObscoleteStockFragment extends Fragment {
    private static ArrayList<ViewObscoleteStockModel> mObscolete = new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt, noobscolete;
    String fragment_name;
    ObscoleteStockAdapter obscoleteStockAdapter;


    public ObscoleteStockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_obscolete_stock, container, false);
        recyclerView = view.findViewById(R.id.restock_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        noobscolete = view.findViewById(R.id.no_obscolete);
        recyclerView.hasFixedSize();
        obscoleteStockAdapter = new ObscoleteStockAdapter(getContext(), mObscolete);
        recyclerView.setAdapter(obscoleteStockAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.product_grid_span)));
        viewobscolete();
        return view;
    }

    private void viewobscolete() {
        showProgress();
        fragment_name = getArguments().getString("fragment_name", fragment_name);
        mObscolete.clear();
        String category = fragment_name;
        Call<List<ViewObscoleteStockModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getObscolete(category);
        call.enqueue(new Callback<List<ViewObscoleteStockModel>>() {
            @Override
            public void onResponse(Call<List<ViewObscoleteStockModel>> call, Response<List<ViewObscoleteStockModel>> response) {
                hideProgress();
                if (response.code() == 200) {
                    mObscolete.addAll(response.body());
                    obscoleteStockAdapter.notifyDataSetChanged();
                    if (mObscolete.size()<1) {
                        noobscolete.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getActivity(),response.message()+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViewObscoleteStockModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
            }

        });
    }

    public static ObscoleteStockFragment newInstance(String fragmentname){
        ObscoleteStockFragment obscoleteStockFragment =new ObscoleteStockFragment();
        Bundle bundle=new Bundle();
        bundle.putString("fragment_name",fragmentname);
        obscoleteStockFragment.setArguments(bundle);
        return obscoleteStockFragment;
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }

}

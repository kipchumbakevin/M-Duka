package com.example.shopkipa;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shopkipa.adapters.GetStockAdapter;
import com.example.shopkipa.adapters.ItemsInTypeAdapter;
import com.example.shopkipa.adapters.TypesInCategoryAdapter;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.GetTypesInCategoryModel;
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
    private static ArrayList<GetTypesInCategoryModel>mTypesArrayList=new ArrayList<>();
    RecyclerView recyclerView,recy;
    RelativeLayout progressLyt;
    TypesInCategoryAdapter typesInCategoryAdapter;
    String fragment_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clothes_stock, container, false);
        recyclerView = view.findViewById(R.id.stock_recyclerView);
//        recy = view.findViewById(R.id.items_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        recyclerView.hasFixedSize();
        typesInCategoryAdapter = new TypesInCategoryAdapter(getActivity(),mTypesArrayList);
        recyclerView.setAdapter(typesInCategoryAdapter);
//        recy.setAdapter(itemsInTypeAdapter);
//        recy.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

          viewTypes();
//           viewStock();
            return view;
        }

    private void viewTypes() {
        showProgress();
        fragment_name = getArguments().getString("fragment_name",fragment_name);
        Toast.makeText(getActivity(),fragment_name+"YEES",Toast.LENGTH_SHORT).show();
        ArrayList<GetTypesInCategoryModel> mRequestsArray;
        mTypesArrayList.clear();
        String categoryname = fragment_name;
        Call<List<GetTypesInCategoryModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getTypes(categoryname);
        call.enqueue(new Callback<List<GetTypesInCategoryModel>>() {
            @Override
            public void onResponse(Call<List<GetTypesInCategoryModel>> call, Response<List<GetTypesInCategoryModel>> response) {
                hideProgress();
                if(response.code()==200){
                    mTypesArrayList.addAll(response.body());
                    typesInCategoryAdapter.notifyDataSetChanged();

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<GetTypesInCategoryModel>> call, Throwable t) {
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

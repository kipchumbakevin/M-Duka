package com.example.shopkipa;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopkipa.adapters.GetStockAdapter;
import com.example.shopkipa.adapters.ItemsInTypeAdapter;
import com.example.shopkipa.adapters.TypesInCategoryAdapter;
import com.example.shopkipa.models.GetGroupModel;
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
    private static ArrayList<GetGroupModel>mGroupArrayList=new ArrayList<>();
    private static ArrayList<GetStockInTypeModel>mStockArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt;
    ItemsInTypeAdapter itemsInTypeAdapter;
    String fragment_name;
    Spinner typeSpinner,groupSpinner;
    private List<String> typeSpinnerArray;
    private ArrayAdapter<String>typeadapter;
    private List<String> groupSpinnerArray;
    private ArrayAdapter<String>groupadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clothes_stock, container, false);
        recyclerView = view.findViewById(R.id.stock_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        typeSpinner = view.findViewById(R.id.typeSpinner);
        groupSpinner = view.findViewById(R.id.groupSpinner);
        recyclerView.hasFixedSize();
        itemsInTypeAdapter = new ItemsInTypeAdapter(getActivity(),mStockArrayList);
        recyclerView.setAdapter(itemsInTypeAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        typeSpinnerArray = new ArrayList<>();

        typeadapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, typeSpinnerArray);

        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(typeadapter);

        groupSpinnerArray = new ArrayList<>();

        groupadapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, groupSpinnerArray);

        groupadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupSpinner.setAdapter(groupadapter);
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String groupname = adapterView.getItemAtPosition(i).toString();
                showProgress();
                fragment_name = getArguments().getString("fragment_name",fragment_name);
                ArrayList<GetTypesInCategoryModel> mRequestsArray;
                String categoryname = fragment_name;
                typeSpinnerArray.clear();
                Call<List<GetTypesInCategoryModel>> call = RetrofitClient.getInstance(getActivity())
                        .getApiConnector()
                        .getTypes(categoryname,groupname);
                call.enqueue(new Callback<List<GetTypesInCategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<GetTypesInCategoryModel>> call, Response<List<GetTypesInCategoryModel>> response) {
                        hideProgress();
                        if(response.code()==200){
                            for(int index= 0;index<response.body().size();index++){
                                typeSpinnerArray.add(response.body().get(index).getTypeName());
                            }
                            Toast.makeText(getActivity(),"Noooo" + response.body().size(),Toast.LENGTH_LONG).show();
                            typeadapter.notifyDataSetChanged();

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

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String typename = adapterView.getItemAtPosition(i).toString();
                showProgress();
                mStockArrayList.clear();
                fragment_name = getArguments().getString("fragment_name",fragment_name);
                String category = fragment_name;
                Call<List<GetStockInTypeModel>> call = RetrofitClient.getInstance(getActivity())
                        .getApiConnector()
                        .getAllStock(typename,category);
                call.enqueue(new Callback<List<GetStockInTypeModel>>() {
                    @Override
                    public void onResponse(Call<List<GetStockInTypeModel>> call, Response<List<GetStockInTypeModel>> response) {
                        hideProgress();
                        if(response.code()==200){
                            mStockArrayList.clear();
                            mStockArrayList.addAll(response.body());
                            itemsInTypeAdapter.notifyDataSetChanged();

                        }
                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<List<GetStockInTypeModel>> call, Throwable t) {
                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }

                });

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewGroup();
            return view;
        }


    private void viewGroup() {
        showProgress();
        fragment_name = getArguments().getString("fragment_name",fragment_name);
        ArrayList<GetGroupModel> mRequestsArray;
        mGroupArrayList.clear();
        String categoryname = fragment_name;
        Call<List<GetGroupModel>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getGroup(categoryname);
        call.enqueue(new Callback<List<GetGroupModel>>() {
            @Override
            public void onResponse(Call<List<GetGroupModel>> call, Response<List<GetGroupModel>> response) {
                hideProgress();
                if(response.code()==200){
                    for(int index= 0;index<response.body().size();index++){
                        groupSpinnerArray.add(response.body().get(index).getName());
                    }
                    groupadapter.notifyDataSetChanged();

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<GetGroupModel>> call, Throwable t) {
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

package com.example.shopkipa.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.ItemsInTypeAdapter;
import com.example.shopkipa.adapters.ViewAdsAdapter;
import com.example.shopkipa.models.GetGroupModel;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.GetTypesInCategoryModel;
import com.example.shopkipa.models.ViewAdsModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StockFragment extends Fragment {
    private  static final String REQUEST_TYPE = "com.example.shopkipa.REQUEST_TYPE" ;
    private static ArrayList<GetTypesInCategoryModel>mTypesArrayList=new ArrayList<>();
    private static ArrayList<GetGroupModel>mGroupArrayList=new ArrayList<>();
    private static ArrayList<GetStockInTypeModel>mStockArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    RelativeLayout progressLyt,noProducts;
    ItemsInTypeAdapter itemsInTypeAdapter;
    LinearLayoutCompat productView;
    String fragment_name;
    SharedPreferencesConfig sharedPreferencesConfig;
    public Boolean done;
    public Spinner typeSpinner,groupSpinner;
    private List<String> typeSpinnerArray;
    private ArrayAdapter<String>typeadapter;
    private List<String> groupSpinnerArray;
    private ArrayAdapter<String>groupadapter;
    private ArrayList<ViewAdsModel> mAdsArray=new ArrayList<>();
    ViewAdsAdapter viewAdsAdapter;
    RecyclerView recyclerView2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);
        recyclerView = view.findViewById(R.id.stock_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        noProducts = view.findViewById(R.id.no_products_view);
        productView = view.findViewById(R.id.products_view);
        typeSpinner = view.findViewById(R.id.typeSpinner);
        groupSpinner = view.findViewById(R.id.groupSpinner);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        viewAdsAdapter = new ViewAdsAdapter(getActivity(),mAdsArray);
        recyclerView2 = view.findViewById(R.id.ads_RecyclerView);
        recyclerView2.setAdapter(viewAdsAdapter);
        recyclerView2.setLayoutManager(linearLayoutManager);
        itemsInTypeAdapter = new ItemsInTypeAdapter(getActivity(), mStockArrayList);
        recyclerView.setAdapter(itemsInTypeAdapter);
        sharedPreferencesConfig = new SharedPreferencesConfig(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.product_grid_span)));

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
        viewGroup();
        new CountDownTimer(10000, 1000) { // 10 seconds, in 1 second intervals
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                viewAds();

            }
        }.start();


        typeSpinner.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    done=true;
                }
                return false;
            }
        });
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String groupname = adapterView.getItemAtPosition(i).toString();
                showProgress();
                fragment_name = getArguments().getString("fragment_name", fragment_name);
                ArrayList<GetTypesInCategoryModel> mRequestsArray;
                final String categoryname = fragment_name;
                typeSpinnerArray.clear();
                mStockArrayList.clear();
                Call<List<GetTypesInCategoryModel>> call = RetrofitClient.getInstance(getActivity())
                        .getApiConnector()
                        .getTypes(categoryname, groupname);
                call.enqueue(new Callback<List<GetTypesInCategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<GetTypesInCategoryModel>> call, Response<List<GetTypesInCategoryModel>> response) {
                        hideProgress();
                        if (response.code() == 200) {
                            for (int index = 0; index < response.body().size(); index++) {
                                mStockArrayList.clear();
                                typeSpinnerArray.add(response.body().get(index).getTypeName());
                            }
                                done=false;
                                String typename = typeSpinnerArray.get(0);
                                String category = categoryname;
                                mStockArrayList.clear();
                                Call<List<GetStockInTypeModel>> call2 = RetrofitClient.getInstance(getActivity())
                                        .getApiConnector()
                                        .getAllStock(typename, category);
                                call2.enqueue(new Callback<List<GetStockInTypeModel>>() {
                                    @Override
                                    public void onResponse(Call<List<GetStockInTypeModel>> call2, Response<List<GetStockInTypeModel>> response) {
                                        hideProgress();
                                        if (response.code() == 200) {
                                            mStockArrayList.addAll(response.body());
                                            itemsInTypeAdapter.notifyDataSetChanged();
                                            if (mStockArrayList.size()<1){
                                                noProducts.setVisibility(View.VISIBLE);
                                                recyclerView.setVisibility(View.GONE);
                                            }
                                        } else {

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<GetStockInTypeModel>> call2, Throwable t) {
                                        hideProgress();
                                        Toast.makeText(getContext(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
                                    }

                                });
                            if (mStockArrayList.size()<1 && typeSpinnerArray.size()<1) {
                                noProducts.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }

                            typeadapter.notifyDataSetChanged();
                            itemsInTypeAdapter.notifyDataSetChanged();

                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GetTypesInCategoryModel>> call, Throwable t) {
                        hideProgress();
                        Toast.makeText(getActivity(), t.getMessage() + "damn", Toast.LENGTH_LONG).show();
                    }

                });

                try {
                    //Your task here
                } catch (Exception e) {
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
                try {
                    String typename = adapterView.getItemAtPosition(i).toString();
                    functions(typename);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                    if (mStockArrayList.size()<1 && typeSpinnerArray.size()<1 && groupSpinnerArray.size()<1) {
                        noProducts.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    groupadapter.notifyDataSetChanged();
                    Log.d("check", ""+sharedPreferencesConfig.readClientsAccessToken());


                }
                else{
                    Log.d("check", ""+sharedPreferencesConfig.readClientsAccessToken());
                    Toast.makeText(getActivity(),response.message()+" found "+sharedPreferencesConfig.readClientsAccessToken() ,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<GetGroupModel>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(),t.getMessage()+"yyy",Toast.LENGTH_LONG).show();
            }

        });

    }
//    private View.OnTouchListener typespinnertt = new View.OnTouchListener() {
//        public boolean onTouch(View v, MotionEvent event) {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                done = true;
//            }
//            return true;
//        }
//    };
    private void functions(String typename){
        if (done){
            showProgress();
            mStockArrayList.clear();
            String category = fragment_name;
            Call<List<GetStockInTypeModel>> call = RetrofitClient.getInstance(getActivity())
                    .getApiConnector()
                    .getAllStock(typename, category);
            call.enqueue(new Callback<List<GetStockInTypeModel>>() {
                @Override
                public void onResponse(Call<List<GetStockInTypeModel>> call, Response<List<GetStockInTypeModel>> response) {
                    hideProgress();
                    if (response.code() == 200) {
                        mStockArrayList.addAll(response.body());
                        itemsInTypeAdapter.notifyDataSetChanged();
                        if (mStockArrayList.size() < 1) {
                            noProducts.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<List<GetStockInTypeModel>> call, Throwable t) {
                    hideProgress();
                    Toast.makeText(getContext(), t.getMessage() + "kkk", Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }

    public static StockFragment newInstance(String fragmentname){
        mStockArrayList.clear();
        StockFragment viewCustomerStockFragment =new StockFragment();
        Bundle bundle=new Bundle();
        bundle.putString("fragment_name",fragmentname);
        viewCustomerStockFragment.setArguments(bundle);
        return viewCustomerStockFragment;
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
}

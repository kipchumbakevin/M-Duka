package com.example.shopkipa.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shopkipa.ClothesStockFragment;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerTabAdapter extends FragmentPagerAdapter {
    private List<GetCategoriesModel> categories = new ArrayList<>();
    private final Context mContext;

    public CustomerTabAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        CustomerTabAdapter.this.notifyDataSetChanged();
        getCategoryList();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
    private void getCategoryList() {
        ArrayList<GetCategoriesModel> mCategoriesArray;
        categories.clear();
        Call<List<GetCategoriesModel>> call = RetrofitClient.getInstance(mContext)
                .getApiConnector()
                .getAllCategories();
        call.enqueue(new Callback<List<GetCategoriesModel>>() {
            @Override
            public void onResponse(Call<List<GetCategoriesModel>> call, Response<List<GetCategoriesModel>> response) {
                if(response.code()==200){
                    categories.addAll(response.body());
                    CustomerTabAdapter.this.notifyDataSetChanged();

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<GetCategoriesModel>> call, Throwable t) {
            }

        });
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String fragmentname = categories.get(position).getName();
            return ClothesStockFragment.newInstance(fragmentname);
        }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }
}

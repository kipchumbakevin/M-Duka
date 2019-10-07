package com.example.shopkipa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CustomerTabAdapter extends FragmentStatePagerAdapter {
    public CustomerTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ViewCustomerStockFragment.newInstance(position);

    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Men";
            case 1:
                return "Ladies";
            case 2:
                return "Boys";
            case 3:
                return "Girls";
            case 4:
                return "Unisex";
            case 5:
                return "Other";
            default:
                return null;
        }
    }
}

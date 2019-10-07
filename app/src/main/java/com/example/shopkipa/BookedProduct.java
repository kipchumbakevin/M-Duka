package com.example.shopkipa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.tabs.TabLayout;

public class BookedProduct extends AppCompatActivity {
    TabLayout tabLayout;
    CheckBox bookedCloth,bookedShoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_product);
        tabLayout = findViewById(R.id.cart_tab);
        bookedCloth = findViewById(R.id.bookedCloth);
        bookedShoe = findViewById(R.id.bookedShoe);
        setTitle("Booked products");
        tabLayout.setTabsFromPagerAdapter(new CustomerTabAdapter(getSupportFragmentManager()));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getTabContent(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        bookedCloth.setChecked(true);
        checkboxes();
    }

    private void checkboxes() {
        bookedCloth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (bookedCloth.isChecked()) {
                    bookedShoe.setChecked(false);
                }
            }
        });
        bookedShoe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (bookedShoe.isChecked()) {
                    bookedCloth.setChecked(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookedCloth.setChecked(true);

    }
    public void getTabContent(int tabIndex){
        ViewCustomerStockFragment tabContentFragment = ViewCustomerStockFragment.newInstance(tabIndex);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragment, tabContentFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}

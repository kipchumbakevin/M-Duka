package com.example.shopkipa;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TabLayout tabLayout;
    CheckBox stockClothes,stockShoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        tabLayout = findViewById(R.id.cart_tab);
        stockClothes = findViewById(R.id.stockCloth);
        stockShoes = findViewById(R.id.stockShoe);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("My stock");
        tabLayout.setTabsFromPagerAdapter(new CustomerTabAdapter(getSupportFragmentManager()));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
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
        if (stockClothes.isChecked()){
            Toast.makeText(MainActivity.this,"Clothes",Toast.LENGTH_SHORT).show();
        }

        checkboxes();
    }

    private void checkboxes() {
        stockClothes.setChecked(true);
        stockClothes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (stockClothes.isChecked()) {
                    stockShoes.setChecked(false);
                    Toast.makeText(MainActivity.this,"Clothes",Toast.LENGTH_SHORT).show();
                }
            }
        });
        stockShoes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (stockShoes.isChecked()) {
                    stockClothes.setChecked(false);
                    Toast.makeText(MainActivity.this,"Shoes",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (stockClothes.isChecked()){
            Toast.makeText(MainActivity.this,"Clothes",Toast.LENGTH_SHORT).show();
        }
        checkboxes();

    }
    public void getTabContent(int tabIndex){
        if (stockShoes.isChecked()) {
            MyStockFragment tabContentFragment = MyStockFragment.newInstance(tabIndex);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment, tabContentFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else if (stockClothes.isChecked()){
            ViewCustomerStockFragment tabContentFragment = ViewCustomerStockFragment.newInstance(tabIndex);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment, tabContentFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_summary) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            View view = getLayoutInflater().inflate(R.layout.income_statement,null);

            alertDialogBuilder.setView(view);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (id == R.id.nav_addexpense) {
            addexpense();

        } else if (id == R.id.nav_addProduct){
            Intent intent = new Intent(MainActivity.this,AddStock.class);
            startActivity(intent);
        }else if (id == R.id.nav_bookedProducts){
            Intent intent = new Intent(MainActivity.this,BookedProduct.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addexpense() {
        final EditText expensetype,expenseamount;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.add_expense,null);
        ImageView closedialog = view.findViewById(R.id.dialog_close);
        ImageView dialogDone = view.findViewById(R.id.dialog_done);
        expensetype = view.findViewById(R.id.expensetype);
        expenseamount = view.findViewById(R.id.expenseamount);

        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        dialogDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exptype = expensetype.getText().toString();
                if (exptype.isEmpty()) {
                    expensetype.setError("Required");
                }if (expenseamount.getText().toString().isEmpty()){
                    expenseamount.setError("Required");
                }else {
                    Toast.makeText(MainActivity.this,exptype + " expense added",Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }
}

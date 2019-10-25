package com.example.shopkipa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopkipa.adapters.CustomerTabAdapter;
import com.example.shopkipa.adapters.GetStockAdapter;
import com.example.shopkipa.adapters.ViewExpensesAdapter;
import com.example.shopkipa.models.AddExpenseModel;
import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String DIALOG_EXPENSES = "My Expenses";
    TabLayout tabLayout;
    RelativeLayout progressLyt;
    CheckBox stockClothes,stockShoes;
    private ArrayList<GetExpenseModel> mExpensesArrayList;
    private List<GetCategoriesModel> categories = new ArrayList<>();
    String categoryname;
    private Context mContext;

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
        progressLyt = findViewById(R.id.progressLoad);
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("My stock");
        getCategoryList();

        if (!categories.isEmpty()){
            String tabIndex = categories.get(0).getName();
            getTabContent(tabIndex);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabId = (String) tab.getText();
                getTabContent(tabId);
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
    public void getTabContent(String tabIndex){
            ClothesStockFragment tabContentFragment = ClothesStockFragment.newInstance(tabIndex);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment, tabContentFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
    }
//    public static ClothesStockFragment newInstance(int val) {
//        ClothesStockFragment fragment = new ClothesStockFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", val);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
        if (id == R.id.action_expenses) {
            startDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startDialog(){
        ViewExpensesDialogFragment dialog = new
                ViewExpensesDialogFragment(mContext);
        dialog.getFragmentManager();
        dialog.show(getSupportFragmentManager(), DIALOG_EXPENSES);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_summary) {
            Intent intent = new Intent(MainActivity.this,SummaryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_addexpense) {
            addexpense();

        } else if (id == R.id.nav_addProduct){
            Intent intent = new Intent(MainActivity.this,AddStock.class);
            startActivity(intent);
        }else if (id == R.id.nav_bookedProducts){
            Intent intent = new Intent(MainActivity.this,BookedProduct.class);
            startActivity(intent);
        }else if (id == R.id.nav_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Manage your shop by a touch of a button.\ndownload now at https://play.google.com/store/apps/details?id=aarhealthcare.com.androidapp";
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(intent,"Share via"));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addexpense() {
        final EditText expenseType,expenseamount;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.add_expense,null);
        ImageView closedialog = view.findViewById(R.id.dialog_close);
        ImageView dialogDone = view.findViewById(R.id.dialog_done);
        expenseType = view.findViewById(R.id.expensetype);
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
                String exptype = expenseType.getText().toString();
                if (exptype.isEmpty()) {
                    expenseType.setError("Required");
                }if (expenseamount.getText().toString().isEmpty()){
                    expenseamount.setError("Required");
                }else {
                    showProgress();
                    final String expensetype = expenseType.getText().toString();
                    String amount = expenseamount.getText().toString();
                            Call<AddExpenseModel> call = RetrofitClient.getInstance(MainActivity.this)
                                    .getApiConnector()
                                    .addnewexpense(expensetype,amount);
                    call.enqueue(new Callback<AddExpenseModel>() {
                        @Override
                        public void onResponse(Call<AddExpenseModel> call, Response<AddExpenseModel> response) {
                            hideProgress();
                            if(response.code()==201){
                                Toast.makeText(MainActivity.this,expensetype + " expense added",Toast.LENGTH_SHORT).show();
                            }
                            else{
                            }

                        }

                        @Override
                        public void onFailure(Call<AddExpenseModel> call, Throwable t) {
                            hideProgress();
                        }
                    });
                }
                    alertDialog.dismiss();
                }
        });
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
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
                    filltabs(tabLayout);

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<GetCategoriesModel>> call, Throwable t) {
            }

        });
    }

    private void filltabs(TabLayout tabLayout) {
        if (!categories.isEmpty()){
            for(int index = 0; index<categories.size();index++){
                String fragmentname = categories.get(index).getName();
                tabLayout.addTab(tabLayout.newTab().setText(fragmentname));
            }
        }
    }
}

package com.example.shopkipa.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.shopkipa.adapters.ViewAdsAdapter;
import com.example.shopkipa.auth.LoginActivity;
import com.example.shopkipa.R;
import com.example.shopkipa.models.SignUpMessagesModel;
import com.example.shopkipa.models.ViewAdsModel;
import com.example.shopkipa.settings.SettingsActivity;
import com.example.shopkipa.models.AddExpenseModel;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.Constants;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
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
//    ImageView imageView;
//    TextView user;
    private ArrayList<GetExpenseModel> mExpensesArrayList;
    private List<GetCategoriesModel> categories = new ArrayList<>();
    private Context mContext;
    private SharedPreferencesConfig sharedPreferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  imageView = findViewById(R.id.imageView);
      //  user = findViewById(R.id.user);
        sharedPreferencesConfig = new SharedPreferencesConfig(MainActivity.this);
        tabLayout = findViewById(R.id.cart_tab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        progressLyt = findViewById(R.id.progressLoad);
        navigationView.setNavigationItemSelectedListener(this);
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
//        String status = sharedPreferencesConfig.readClientsStatus();
//        if(status.contentEquals(Constants.ACTIVE_CONSTANT)) {
//            String username = sharedPreferencesConfig.readClientsUsername();
//          //  user.setText(username);
//
//            getFirstLetterInCircularBackground(imageView, username);
//        }
//        else {
//            String username = user.getText().toString();
//            getFirstLetterInCircularBackground(imageView, username);
//
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void getTabContent(String tabIndex){
            StockFragment tabContentFragment = StockFragment.newInstance(tabIndex);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment, tabContentFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
    }
//    public static StockFragment newInstance(int val) {
//        StockFragment fragment = new StockFragment();
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }else if (id == R.id.action_help){
            Intent intent = new Intent(MainActivity.this,HelpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

//    public void startDialog(){
//        ViewExpensesDialogFragment dialog = new
//                ViewExpensesDialogFragment(mContext);
//        dialog.getFragmentManager();
//        dialog.show(getSupportFragmentManager(), DIALOG_EXPENSES);
//    }

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
        }else if (id == R.id.nav_logout){
            logOut(); }
        //else if (id == R.id.nav_updates){
//            Intent intent = new Intent(MainActivity.this,UpdatesActivity.class);
//            startActivity(intent);
//        }else if (id == R.id.nav_recipes){
//            Intent intent = new Intent(MainActivity.this,RecipesActivity.class);
//            startActivity(intent);}
            else if (id == R.id.nav_restock) {
            Intent intent = new Intent(MainActivity.this, RestockActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Manage your shop by a touch of a button.\ndownload now at https://play.google.com/store/apps/details?id=aarhealthcare.com.androidapp";
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(intent,"Share via"));
        }
            else if (id == R.id.nav_shoppinglist){
                Intent intent = new Intent(MainActivity.this,ShoppingListActivity.class);
                startActivity(intent);
                finish();
        }
            else if (id == R.id.nav_given){
            Intent intent = new Intent(MainActivity.this,GivenStockActivity.class);
            startActivity(intent);
            finish();
        }
            else if (id == R.id.nav_obscolete){
            Intent intent = new Intent(MainActivity.this,ObscoleteStockActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getFirstLetterInCircularBackground(ImageView imageView, String username){
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//        generate random color
//        int color = generator.getColor(getItem());

        int color = generator.getRandomColor();
        String firstLetter = String.valueOf(username.charAt(0));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        imageView.setImageDrawable(drawable);
    }

    private void logOut() {
        showProgress();
        Call<SignUpMessagesModel> call = RetrofitClient.getInstance(MainActivity.this)
                .getApiConnector()
                .logOut();
        call.enqueue(new Callback<SignUpMessagesModel>() {
            @Override
            public void onResponse(Call<SignUpMessagesModel> call, Response<SignUpMessagesModel> response) {
                hideProgress();
                if(response.code()==200){
                    sharedPreferencesConfig.clear();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("logout", sharedPreferencesConfig.readClientsAccessToken());

                }
                else{
                    Toast.makeText(MainActivity.this,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SignUpMessagesModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(MainActivity.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this,expensetype + " Expense added successfully",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Internal server error",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<AddExpenseModel> call, Throwable t) {
                            Toast.makeText(MainActivity.this,"Network error",Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    });
                }
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
        Call<List<GetCategoriesModel>> call = RetrofitClient.getInstance(MainActivity.this)
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

package com.example.shopkipa.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.ChangeDetailsModel;
import com.example.shopkipa.models.SignUpMessagesModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePersonalInfo extends AppCompatActivity {
    Button submitChanges;
    RelativeLayout progress,username,first,last,loca;
    TextView editUsername,editFirst,editLast,editLocation;
    SharedPreferencesConfig sharedPreferencesConfig;
    private String clientsFirstName,clientsLastName,clientsUsername,clientsLocation;
    int dd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_info);
        editUsername = findViewById(R.id.set_username);
        editFirst = findViewById(R.id.set_firstname);
        username = findViewById(R.id.username_relative);
        first = findViewById(R.id.firstname_relative);
        last = findViewById(R.id.lastname_relative);
        loca = findViewById(R.id.location_relative);
        editLast = findViewById(R.id.set_lastname);
        progress = findViewById(R.id.progressLoad);
        editLocation = findViewById(R.id.set_location);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        submitChanges = findViewById(R.id.submit_changes);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadDetails();
        submitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNetworkAvailable()){
                    Toast.makeText(ChangePersonalInfo.this,"Check your network connection",Toast.LENGTH_SHORT).show();
                }else {
                    changeInfo();
                }
            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dd = 1;
                changeDetailsDialog();
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dd = 2;
                changeDetailsDialog();
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dd = 3;
                changeDetailsDialog();
            }
        });
        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dd = 4;
                changeDetailsDialog();
            }
        });
    }
    private void changeDetailsDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.change_details_edittext,null);
        TextView title = view.findViewById(R.id.dialog_title);
        final EditText editText = view.findViewById(R.id.edit_them_details);
        if (dd==1) title.setText("Username:");
        if (dd==2) title.setText("First name:");
        if (dd==3) title.setText("Last name:");
        if (dd==4) title.setText("Location:");
        alertDialogBuilder
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String changed = editText.getText().toString();
                        if (!editText.getText().toString().isEmpty()){
                            if (dd==1) editUsername.setText(changed);
                            if (dd==2) editFirst.setText(changed);
                            if (dd==3) editLast.setText(changed);
                            if (dd==4) editLocation.setText(changed);
                        }
                    }
                });
        alertDialogBuilder.setView(view);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void loadDetails() {
        editUsername.setText(sharedPreferencesConfig.readClientsUsername());
        editFirst.setText(sharedPreferencesConfig.readClientsFirstName());
        editLast.setText(sharedPreferencesConfig.readClientsLastName());
        editLocation.setText(sharedPreferencesConfig.readClientsLocation());
    }

    private void changeInfo() {
        String username,lastname,firstname,location;
        username = editUsername.getText().toString();
        firstname = editFirst.getText().toString();
        lastname = editLast.getText().toString();
        location = editLocation.getText().toString();
        SignUpMessagesModel signUpMessagesModel = new SignUpMessagesModel();
        showProgress();
        Call<ChangeDetailsModel> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .changeDetails(username,firstname,lastname,location);
        call.enqueue(new Callback<ChangeDetailsModel>() {
            @Override
            public void onResponse(Call<ChangeDetailsModel> call, Response<ChangeDetailsModel> response) {
                hideProgress();
                if(response.isSuccessful()){
                    clientsFirstName = response.body().getUser().getFirstName();
                    clientsLastName = response.body().getUser().getLastName();
                    clientsLocation = response.body().getUser().getLocation();
                    clientsUsername = response.body().getUser().getUsername();
                    sharedPreferencesConfig.saveChangedDetails(clientsFirstName,clientsLastName,clientsLocation,clientsUsername);
                    Toast.makeText(ChangePersonalInfo.this,"Done",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePersonalInfo.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ChangePersonalInfo.this,"The username has already been taken",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangeDetailsModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePersonalInfo.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        progress.setVisibility(View.GONE);
    }

//    public static class SettingsFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            addPreferencesFromResource(R.xml.personal_information_preferences);
//            SharedPreferencesConfig sharedPreferencesConfig;
//            bindSummaryValue(findPreference("username_edit_preference"));
//            bindSummaryValue(findPreference("first_name_edit_preference"));
//            bindSummaryValue(findPreference("last_name_edit_preference"));
//            bindSummaryValue(findPreference("location_edit_preference"));
//            final String user = findPreference("username_edit_preference").getSummary().toString();
//            final String first = findPreference("first_name_edit_preference").getSummary().toString();
//            final String last = findPreference("last_name_edit_preference").getSummary().toString();
//
//            final Context context = getContext();
//            sharedPreferencesConfig = new SharedPreferencesConfig(context);
//
//
//            Preference preferenceUsername, preferencefirstname, preferencelastname,preferenceLocation;
//            preferenceUsername = findPreference("username_edit_preference");
//            preferencefirstname = findPreference("first_name_edit_preference");
//            preferencelastname = findPreference("last_name_edit_preference");
//            preferenceLocation = findPreference("location_edit_preference");
//
//            String username = sharedPreferencesConfig.readClientsUsername();
//            String firstname = sharedPreferencesConfig.readClientsFirstName();
//            String lastname = sharedPreferencesConfig.readClientsLastName();
//            String location = sharedPreferencesConfig.readClientsLocation();
//
//            if (preferenceUsername != null) {
//                preferenceUsername.setSummary(username);
//            }
//            if (preferencefirstname != null) {
//                preferencefirstname.setSummary(firstname);
//            }
//            if (preferencelastname != null) {
//                preferencelastname.setSummary(lastname);
//            }
//            if (preferenceLocation != null){
//                preferenceLocation.setSummary(location);
//            }
//        }
//    }
//    private static void bindSummaryValue(Preference preference){
//        preference.setOnPreferenceChangeListener(listener);
//        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
//        .getString(preference.getKey(),""));
//
//    }
//    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
//        @Override
//        public boolean onPreferenceChange(Preference preference, Object newValue) {
//            String value = newValue.toString();
//            if (preference instanceof EditTextPreference){
//                preference.setSummary(value);
//            }
//            return false;
//        }
//    };
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Intent intent = new Intent (ChangePersonalInfo.this,SettingsActivity.class);
//        startActivity(intent);
//        finish();
//
//    }
}
